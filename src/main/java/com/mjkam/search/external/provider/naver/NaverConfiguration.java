package com.mjkam.search.external.provider.naver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.provider.naver")
@Getter
@Setter
public class NaverConfiguration {
    private String url;
    private String clientId;
    private String clientSecret;
}