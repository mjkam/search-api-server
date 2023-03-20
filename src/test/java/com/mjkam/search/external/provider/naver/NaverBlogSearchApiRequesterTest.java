package com.mjkam.search.external.provider.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjkam.search.external.BlogSearchApiResponse;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.ExternalBaseTest;
import com.mjkam.search.external.SortingType;
import com.mjkam.search.external.provider.naver.support.NaverApiResponseCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class NaverBlogSearchApiRequesterTest extends ExternalBaseTest {
    private NaverBlogSearchApiRequester sut;
    private MockRestServiceServer mockRestServiceServer;


    @BeforeEach
    void setup() {
        RestTemplate restTemplate = new RestTemplate();
        NaverConfiguration naverConfiguration = new NaverConfiguration();
        naverConfiguration.setUrl(DUMMY_API_ENDPOINT);
        naverConfiguration.setClientId(DUMMY_NAVER_CLIENT_ID);
        naverConfiguration.setClientSecret(DUMMY_NAVER_CLIENT_SECRET);

        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        sut = new NaverBlogSearchApiRequester(restTemplate, naverConfiguration);
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
        BlogSearchApiResponse response = sut.execute(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCountFromServer);
        assertThat(response.getPageableCount()).isEqualTo(expectPageableCount);
        assertThat(response.getDocuments().size()).isEqualTo(expectItemCount);
    }

    private void mockNaverApiServer(int totalCountFromServer, int page, int size) throws JsonProcessingException {
        NaverApiResponse expectResponse = NaverApiResponseCreator.create(totalCountFromServer, page, size);
        mockRestServiceServer
                .expect(requestTo(url(DUMMY_API_ENDPOINT, DUMMY_QUERY, page, size, DUMMY_SORTING_TYPE)))
                .andExpect(header("X-Naver-Client-Id", DUMMY_NAVER_CLIENT_ID))
                .andExpect(header("X-Naver-Client-Secret", DUMMY_NAVER_CLIENT_SECRET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expectResponse), MediaType.APPLICATION_JSON));
    }

    private String url(String endpoint, String query, int page, int size, SortingType sortingType) {
        int start = (page - 1) * size + 1;
        return String.format("%s?query=%s&start=%d&display=%d&sort=%s",
                endpoint, query, start, size, sortingType.getNaverName());
    }
}
