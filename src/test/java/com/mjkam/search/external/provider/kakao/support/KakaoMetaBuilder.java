package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.kakao.KakaoResponse;
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

    public KakaoResponse.Meta build() {
        KakaoResponse.Meta meta = new KakaoResponse.Meta();
        ReflectionTestUtils.setField(meta, "totalCount", totalCount);
        ReflectionTestUtils.setField(meta, "pageableCount", pageableCount);
        ReflectionTestUtils.setField(meta, "isEnd", isEnd);

        return meta;
    }
}
