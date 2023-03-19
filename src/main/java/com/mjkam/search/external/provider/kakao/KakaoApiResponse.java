package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoApiResponse {
    @JsonProperty("meta")
    private KakaoMeta kakaoMeta;
    private List<KakaoDocument> documents;

    public int getTotalCount() {
        return this.kakaoMeta.getTotalCount();
    }

    public int getPageableCount() {
        return this.kakaoMeta.getPageableCount();
    }

    public List<KakaoDocument> getDocumentsForResponse(int page, int size) {
        int totalPageNum = kakaoMeta.getPageableCount() / size;
        if (kakaoMeta.getPageableCount() % size != 0) {
            totalPageNum++;
        }

        if (page > totalPageNum) {
            return List.of();
        }
        return documents;
    }
}