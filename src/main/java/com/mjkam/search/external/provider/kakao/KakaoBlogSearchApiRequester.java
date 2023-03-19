package com.mjkam.search.external.provider.kakao;

import com.mjkam.search.external.BlogSearchApiException;
import com.mjkam.search.external.BlogSearchApiRequester;
import com.mjkam.search.external.BlogSearchApiResponse;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.provider.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoBlogSearchApiRequester implements BlogSearchApiRequester {
    private final RestTemplate restTemplate;
    private final KakaoConfiguration kakaoConfiguration;

    @Override
    public BlogSearchApiResponse execute(BlogSearchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", kakaoConfiguration.getKey()));
        HttpEntity<Object> header = new HttpEntity<>(headers);

        String uri = UriComponentsBuilder.fromUriString(kakaoConfiguration.getUrl())
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSortingType().getKakaoName())
                .toUriString();

        ResponseEntity<KakaoApiResponse> response =
                restTemplate.exchange(uri, HttpMethod.GET, header, KakaoApiResponse.class);

        KakaoApiResponse responseBody = response.getBody();
        if (responseBody == null) {
            throw new BlogSearchApiException("Body should be not null");
        }

        return BlogSearchApiResponse.fromKakao(
                request.getPage(), request.getSize(), responseBody);
    }

    @Override
    public boolean isMatch(ProviderType providerType) {
        return providerType.equals(ProviderType.KAKAO);
    }
}
