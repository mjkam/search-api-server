package com.mjkam.search.external.provider.kakao.builder;

import com.mjkam.search.external.provider.kakao.KakaoMeta;
import org.springframework.test.util.ReflectionTestUtils;

public final class KakaoMetaBuilder {
    private Integer totalCount = 0;
    private Integer pageableCount;
    private Boolean isEnd = false;

    private KakaoMetaBuilder() {
    }

    public static KakaoMetaBuilder kakaoMeta() {
        return new KakaoMetaBuilder();
    }

    public KakaoMetaBuilder totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public KakaoMetaBuilder pageableCount(Integer pageableCount) {
        this.pageableCount = pageableCount;
        return this;
    }

    public KakaoMetaBuilder isEnd(Boolean isEnd) {
        this.isEnd = isEnd;
        return this;
    }

    public KakaoMeta build() {
        KakaoMeta kakaoMeta = new KakaoMeta();
        ReflectionTestUtils.setField(kakaoMeta, "totalCount", totalCount);
        ReflectionTestUtils.setField(kakaoMeta, "pageableCount", pageableCount);
        ReflectionTestUtils.setField(kakaoMeta, "isEnd", isEnd);

        return kakaoMeta;
    }
}
