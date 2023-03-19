package com.mjkam.search.external.provider.kakao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KakaoConfigurationTest {
    @Autowired
    private KakaoConfiguration kakaoConfiguration;

    @Test
    @DisplayName("kakaoConfiguration 의 모든 필드는 값이 있어야 함")
    void allKakaoConfigurationFieldsShouldNotBeBlank() {
        assertThat(kakaoConfiguration.getKey()).isNotBlank();
        assertThat(kakaoConfiguration.getUrl()).isNotBlank();
    }
}
