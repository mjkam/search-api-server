package com.mjkam.search.external.provider.naver;

import com.mjkam.search.external.BlogSearchApiRequester;
import com.mjkam.search.external.BlogSearchApiResponse;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.provider.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverBlogSearchApiRequester implements BlogSearchApiRequester {
    private final RestTemplate restTemplate;
    private final NaverConfiguration naverConfiguration;

    @Override
    public BlogSearchApiResponse execute(BlogSearchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverConfiguration.getClientId());
        headers.set("X-Naver-Client-Secret", naverConfiguration.getClientSecret());
        HttpEntity<Object> header = new HttpEntity<>(headers);

        try {
            String query = URLEncoder.encode(request.getQuery(), StandardCharsets.UTF_8);
            String urlStr = String.format("%s?query=%s&start=%s&display=%s&sort=%s",
                    naverConfiguration.getUrl(),
                    query,
                    request.getStartPosition(),
                    request.getSize(),
                    request.getSortingType().getNaverName());
            URL url = new URL(urlStr);
            System.out.println(url.toURI());
            ResponseEntity<NaverApiResponse> response =
                    restTemplate.exchange(url.toURI(), HttpMethod.GET, header, NaverApiResponse.class);

            NaverApiResponse responseBody = response.getBody();
            if (responseBody == null) {
                throw new IllegalStateException("Body should be not null");
            }

            return BlogSearchApiResponse.fromNaver(responseBody);
        } catch (Exception e) {
            log.warn("Provider {} API FAILED | Msg: {} | Request: {} | url: {} | clientId: {} | clientSecret: {}",
                    ProviderType.NAVER, e.getMessage(), request, naverConfiguration.getUrl(), naverConfiguration.getClientId(), naverConfiguration.getClientSecret());
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean isMatch(ProviderType providerType) {
        return providerType.equals(ProviderType.NAVER);
    }
}
