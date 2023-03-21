package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.kakao.KakaoResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public final class KakaoClientResponseBuilder {
    private KakaoResponse.Meta meta;
    private List<KakaoResponse.Document> documents;

    private KakaoClientResponseBuilder() {
    }

    public static KakaoClientResponseBuilder kakaoApiResponse() {
        return new KakaoClientResponseBuilder();
    }

    public KakaoClientResponseBuilder kakaoMeta(KakaoResponse.Meta meta) {
        this.meta = meta;
        return this;
    }

    public KakaoClientResponseBuilder documents(List<KakaoResponse.Document> documents) {
        this.documents = documents;
        return this;
    }

    public KakaoResponse build() {
        KakaoResponse response = new KakaoResponse();
        ReflectionTestUtils.setField(response, "meta", meta);
        ReflectionTestUtils.setField(response, "documents", documents);

        return response;
    }
}
