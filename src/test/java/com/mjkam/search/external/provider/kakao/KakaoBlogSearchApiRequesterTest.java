package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjkam.search.external.BlogSearchApiResponse;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.SortingType;
import com.mjkam.search.external.provider.kakao.support.KakaoApiResponseCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class KakaoBlogSearchApiRequesterTest {
    private KakaoBlogSearchApiRequester sut;
    private MockRestServiceServer mockRestServiceServer;

    private final String API_KEY = "TEMP_API_KEY";
    private final String API_ENDPOINT = "http://test.com";
    private final String query = "TEST";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        RestTemplate restTemplate = new RestTemplate();
        KakaoConfiguration kakaoConfiguration = new KakaoConfiguration();
        kakaoConfiguration.setKey(API_KEY);
        kakaoConfiguration.setUrl(API_ENDPOINT);

        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        sut = new KakaoBlogSearchApiRequester(restTemplate, kakaoConfiguration);
    }

    @ParameterizedTest
    @DisplayName("kakao api 정상 호출 테스트")
    @CsvSource(delimiter = ':',
            value = {
                    "80000:100:10:10:10",
                    "100:100:9:7:7",
                    "200:100:15:9:0",
                    "3000:13:2:7:6"})
    void callKakaoApiNormalTest(int totalCount, int pageableCount, int page, int size, int resultDocumentCount) throws JsonProcessingException {
        //given
        SortingType sortingType = SortingType.ACCURACY;
        BlogSearchRequest request = new BlogSearchRequest(query, page, size, sortingType);

        KakaoApiResponse kakaoApiResponse = KakaoApiResponseCreator.create(totalCount, pageableCount, page, size);

        mockRestServiceServer
                .expect(requestTo(url(query, page, size, sortingType)))
                .andExpect(header(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", API_KEY)))
                .andRespond(MockRestResponseCreators.withSuccess(objectMapper.writeValueAsString(kakaoApiResponse), MediaType.APPLICATION_JSON));

        //when
        BlogSearchApiResponse response = sut.execute(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCount);
        assertThat(response.getPageableCount()).isEqualTo(pageableCount);
        assertThat(response.getDocuments().size()).isEqualTo(resultDocumentCount);
    }

    private String url(String query, int page, int size, SortingType sortingType) {
        return String.format("%s?query=%s&page=%d&size=%d&sort=%s",
                API_ENDPOINT, query, page, size, sortingType.getKakaoName());
    }
}
