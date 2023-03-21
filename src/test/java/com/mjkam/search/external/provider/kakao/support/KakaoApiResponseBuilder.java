package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.kakao.KakaoResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public final class KakaoApiResponseBuilder {
    private KakaoResponse.Meta meta;
    private List<KakaoResponse.Document> documents;

    private KakaoApiResponseBuilder() {
    }

    public static KakaoApiResponseBuilder kakaoApiResponse() {
        return new KakaoApiResponseBuilder();
    }

    public KakaoApiResponseBuilder kakaoMeta(KakaoResponse.Meta meta) {
        this.meta = meta;
        return this;
    }

    public KakaoApiResponseBuilder documents(List<KakaoResponse.Document> documents) {
        this.documents = documents;
        return this;
    }

    public KakaoResponse build() {
        KakaoResponse response = new KakaoResponse();
        ReflectionTestUtils.setField(response, "kakaoMeta", meta);
        ReflectionTestUtils.setField(response, "documents", documents);

        return response;
    }
}
