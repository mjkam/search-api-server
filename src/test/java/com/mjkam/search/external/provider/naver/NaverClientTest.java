package com.mjkam.search.external.provider.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjkam.search.external.BlogSearchResultDto;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.ExternalBaseTest;
import com.mjkam.search.external.provider.ClientResponse;
import com.mjkam.search.external.provider.SortingType;
import com.mjkam.search.external.provider.naver.support.NaverApiResponseCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class NaverClientTest extends ExternalBaseTest {
    private NaverClient sut;
    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setup() {
        RestTemplate restTemplate = new RestTemplate();
        NaverConfiguration naverConfiguration = new NaverConfiguration();
        naverConfiguration.setUrl(DUMMY_API_ENDPOINT);
        naverConfiguration.setClientId(DUMMY_NAVER_CLIENT_ID);
        naverConfiguration.setClientSecret(DUMMY_NAVER_CLIENT_SECRET);

        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        sut = new NaverClient(restTemplate, naverConfiguration);
    }

    @Test
    @DisplayName("Naver api 호출 시 response body 가 null 이면 예외 발생")
    void throwException_whenResponseBodyIsNull() {
        //given
        BlogSearchRequest request = new BlogSearchRequest(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE);

        mockRestServiceServer
                .expect(requestTo(url(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE)))
                .andRespond(MockRestResponseCreators.withNoContent());

        //when then
        Assertions.assertThatThrownBy(() -> sut.execute(request))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Naver api 호출 시 잘못된 host 로 요청하면 예외 발생")
    void throwException_whenUseInvalidHostName() {
        //given
        BlogSearchRequest request = new BlogSearchRequest(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE);

        mockRestServiceServer
                .expect(requestTo(url(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE)))
                .andRespond((response) -> { throw new UnknownHostException();});

        //when then
        Assertions.assertThatThrownBy(() -> sut.execute(request))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Naver Api 호출에서 http status 200 이 아닌 응답이 오면 예외 발생")
    void throwException_whenResponseStatusCodeIsNot200() {
        //given
        BlogSearchRequest request = new BlogSearchRequest(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE);

        mockRestServiceServer
                .expect(requestTo(url(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE)))
                .andRespond((response) -> { throw new RestClientException("");});

        //when then
        Assertions.assertThatThrownBy(() -> sut.execute(request))
                .isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("naver api 호출 성공 테스트")
    @CsvSource(delimiter = ':',
            value = {
                    "10:10:100:100:10",
                    "9:7:100:100:7",
                    "15:9:100:100:0",
                    "2:7:13:13:6",
                    "10:20:2000:200:20"
            })
    void callNaverApiSuccess(int page, int size, int totalCountFromServer, int expectPageableCount, int expectItemCount) throws JsonProcessingException {
        //given
        BlogSearchRequest request = new BlogSearchRequest(DUMMY_QUERY, page, size, DUMMY_SORTING_TYPE);
        mockNaverApiServer(totalCountFromServer, page, size);

        //when
        ClientResponse response = sut.execute(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCountFromServer);
        assertThat(response.getPageableCount()).isEqualTo(expectPageableCount);
        assertThat(response.getDocuments().size()).isEqualTo(expectItemCount);
    }

    private void mockNaverApiServer(int totalCountFromServer, int page, int size) throws JsonProcessingException {
        NaverResponse expectResponse = NaverApiResponseCreator.create(totalCountFromServer, page, size);
        mockRestServiceServer
                .expect(requestTo(url(DUMMY_QUERY, page, size, DUMMY_SORTING_TYPE)))
                .andExpect(header("X-Naver-Client-Id", DUMMY_NAVER_CLIENT_ID))
                .andExpect(header("X-Naver-Client-Secret", DUMMY_NAVER_CLIENT_SECRET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expectResponse), MediaType.APPLICATION_JSON));
    }

    private String url(String query, int page, int size, SortingType sortingType) {
        int start = (page - 1) * size + 1;
        return String.format("%s?query=%s&start=%d&display=%d&sort=%s",
                DUMMY_API_ENDPOINT, query, start, size, sortingType.getNaverName());
    }
}
