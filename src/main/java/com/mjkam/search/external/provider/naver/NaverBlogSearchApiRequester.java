package com.mjkam.search.external.provider.naver;

import com.mjkam.search.external.BlogSearchApiException;
import com.mjkam.search.external.BlogSearchApiRequester;
import com.mjkam.search.external.BlogSearchApiResponse;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.provider.ProviderType;
import com.mjkam.search.external.provider.kakao.KakaoApiResponse;
import com.mjkam.search.external.provider.kakao.KakaoConfiguration;
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
public class NaverBlogSearchApiRequester implements BlogSearchApiRequester {
    private final RestTemplate restTemplate;
    private final NaverConfiguration naverConfiguration;

    @Override
    public BlogSearchApiResponse execute(BlogSearchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverConfiguration.getClientId());
        headers.set("X-Naver-Client-Secret", naverConfiguration.getClientSecret());
        HttpEntity<Object> header = new HttpEntity<>(headers);

        String uri = UriComponentsBuilder.fromUriString(naverConfiguration.getUrl())
                .queryParam("query", request.getQuery())
                .queryParam("start", request.getStartPosition())
                .queryParam("display", request.getSize())
                .queryParam("sort", request.getSortingType().getNaverName())
                .toUriString();

        ResponseEntity<NaverApiResponse> response =
                restTemplate.exchange(uri, HttpMethod.GET, header, NaverApiResponse.class);

        NaverApiResponse responseBody = response.getBody();
        if (responseBody == null) {
            throw new BlogSearchApiException("Body should be not null");
        }

        return BlogSearchApiResponse.fromNaver(responseBody);
    }

    @Override
    public boolean isMatch(ProviderType providerType) {
        return providerType.equals(ProviderType.NAVER);
    }
}
