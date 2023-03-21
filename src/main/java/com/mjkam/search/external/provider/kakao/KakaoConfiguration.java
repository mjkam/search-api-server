package com.mjkam.search.external.provider.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.provider.kakao")
@Getter
@Setter
public class KakaoConfiguration {
    private String url;
    private String key;
}
