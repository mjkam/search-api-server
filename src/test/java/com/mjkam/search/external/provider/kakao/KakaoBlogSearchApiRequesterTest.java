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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.mjkam.search.external.provider.kakao.support.KakaoApiResponseBuilder.kakaoApiResponse;
import static com.mjkam.search.external.provider.kakao.support.KakaoMetaBuilder.kakaoMeta;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class KakaoBlogSearchApiRequesterTest {
    private KakaoBlogSearchApiRequester sut;
    private MockRestServiceServer mockRestServiceServer;

    private final String API_KEY = "TEMP_API_KEY";
    private final String API_ENDPOINT = "http://test.com";

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

    @Test
    @DisplayName("kakao api 정상 호출 테스트")
    void callKakaoApiNormalTest() throws JsonProcessingException {
        //given
        String query = "TEST";
        int page = 10;
        int size = 10;
        SortingType sortingType = SortingType.ACCURACY;

        int totalCount = 100000000;
        int pageableCount = 800;

        KakaoApiResponse kakaoApiResponse = KakaoApiResponseCreator.create(totalCount, pageableCount, page, size);

        mockRestServiceServer
                .expect(requestTo(url(query, page, size, sortingType)))
                .andExpect(header(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", API_KEY)))
                .andRespond(MockRestResponseCreators.withSuccess(objectMapper.writeValueAsString(kakaoApiResponse), MediaType.APPLICATION_JSON));

        BlogSearchRequest request = new BlogSearchRequest(query, page, size, sortingType);

        //when
        BlogSearchApiResponse response = sut.execute(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCount);
        assertThat(response.getPageableCount()).isEqualTo(800);
        assertThat(response.getDocuments().size()).isEqualTo(10);
    }

    private String url(String query, int page, int size, SortingType sortingType) {
        return String.format("%s?query=%s&page=%d&size=%d&sort=%s",
                API_ENDPOINT, query, page, size, sortingType.getKakaoName());
    }
}
