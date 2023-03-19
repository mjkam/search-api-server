package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.kakao.KakaoApiResponse;
import com.mjkam.search.external.provider.kakao.KakaoDocument;
import com.mjkam.search.external.provider.kakao.KakaoMeta;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public final class KakaoApiResponseBuilder {
    private KakaoMeta kakaoMeta;
    private List<KakaoDocument> documents;

    private KakaoApiResponseBuilder() {
    }

    public static KakaoApiResponseBuilder kakaoApiResponse() {
        return new KakaoApiResponseBuilder();
    }

    public KakaoApiResponseBuilder kakaoMeta(KakaoMeta kakaoMeta) {
        this.kakaoMeta = kakaoMeta;
        return this;
    }

    public KakaoApiResponseBuilder documents(List<KakaoDocument> documents) {
        this.documents = documents;
        return this;
    }

    public KakaoApiResponse build() {
        KakaoApiResponse response = new KakaoApiResponse();
        ReflectionTestUtils.setField(response, "kakaoMeta", kakaoMeta);
        ReflectionTestUtils.setField(response, "documents", documents);

        return response;
    }
}
