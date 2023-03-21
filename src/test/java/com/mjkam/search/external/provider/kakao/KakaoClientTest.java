package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjkam.search.external.provider.ClientResponse;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.ExternalBaseTest;
import com.mjkam.search.external.provider.SortingType;
import com.mjkam.search.external.provider.kakao.support.KakaoApiResponseCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class KakaoClientTest extends ExternalBaseTest {
    private KakaoClient sut;
    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setup() {
        RestTemplate restTemplate = new RestTemplate();
        KakaoConfiguration kakaoConfiguration = new KakaoConfiguration();
        kakaoConfiguration.setKey(DUMMY_KAKAO_API_KEY);
        kakaoConfiguration.setUrl(DUMMY_API_ENDPOINT);

        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        sut = new KakaoClient(restTemplate, kakaoConfiguration);
    }

    @Test
    @DisplayName("Kakao api 호출 시 response body 가 null 이면 예외 발생")
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
    @DisplayName("Kakao api 호출 시 잘못된 host 로 요청하면 예외 발생")
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
    @DisplayName("Kakao Api 호출에서 http status 200 이 아닌 응답이 오면 예외 발생")
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
    @DisplayName("kakao api 정상 호출 테스트")
    @CsvSource(delimiter = ':',
            value = {
                    "10:10:80000:800:10",
                    "9:7:100:100:7",
                    "15:9:200:100:0",
                    "2:7:100:13:6"
            })
    void callKakaoApiNormalTest(int page, int size, int totalCountFromServer, int pageableCountFromServer, int expectDocumentCount) throws JsonProcessingException {
        //given
        BlogSearchRequest request = new BlogSearchRequest(DUMMY_QUERY, page, size, DUMMY_SORTING_TYPE);
        mockKakaoApiServer(totalCountFromServer, pageableCountFromServer, page, size);

        //when
        ClientResponse response = sut.execute(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCountFromServer);
        assertThat(response.getPageableCount()).isEqualTo(pageableCountFromServer);
        assertThat(response.getDocuments().size()).isEqualTo(expectDocumentCount);
    }

    private void mockKakaoApiServer(int totalCountFromServer, int pageableCountFromServer, int page, int size) throws JsonProcessingException {
        KakaoResponse kakaoResponse =
                KakaoApiResponseCreator.createKakaoResponse(totalCountFromServer, pageableCountFromServer, page, size);

        mockRestServiceServer
                .expect(requestTo(url(DUMMY_QUERY, page, size, DUMMY_SORTING_TYPE)))
                .andExpect(header(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", DUMMY_KAKAO_API_KEY)))
                .andRespond(MockRestResponseCreators.withSuccess(objectMapper.writeValueAsString(kakaoResponse), MediaType.APPLICATION_JSON));
    }

    private String url(String query, int page, int size, SortingType sortingType) {
        return String.format("%s?query=%s&page=%d&size=%d&sort=%s",
                DUMMY_API_ENDPOINT, query, page, size, sortingType.getKakaoName());
    }
}
