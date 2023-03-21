package com.mjkam.search.external.provider.kakao;

import com.mjkam.search.external.provider.ProviderClient;
import com.mjkam.search.external.provider.ClientResponse;
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

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient implements ProviderClient {
    private final RestTemplate restTemplate;
    private final KakaoConfiguration kakaoConfiguration;

    @Override
    public ClientResponse execute(BlogSearchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", kakaoConfiguration.getKey()));
        HttpEntity<Object> header = new HttpEntity<>(headers);

        try {
            String query = URLEncoder.encode(request.getQuery(), StandardCharsets.UTF_8);
            String urlStr = String.format("%s?query=%s&page=%s&size=%s&sort=%s",
                    kakaoConfiguration.getUrl(),
                    query,
                    request.getPage(),
                    request.getSize(),
                    request.getSortingType().getKakaoName());
            URL url = new URL(urlStr);

            ResponseEntity<KakaoResponse> response =
                    restTemplate.exchange(url.toURI(), HttpMethod.GET, header, KakaoResponse.class);

            KakaoResponse responseBody = response.getBody();
            if (responseBody == null) {
                throw new IllegalStateException("Body should be not null");
            }

            return ClientResponse.fromKakao(
                    request.getPage(), request.getSize(), responseBody);
        } catch (Exception e) {
            log.warn("Provider {} API FAILED | Msg: {} | Request: {} | url: {} | key: {}",
                    ProviderType.KAKAO, e.getMessage(), request, kakaoConfiguration.getUrl(), kakaoConfiguration.getKey());
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean isMatch(ProviderType providerType) {
        return providerType.equals(ProviderType.KAKAO);
    }
}
