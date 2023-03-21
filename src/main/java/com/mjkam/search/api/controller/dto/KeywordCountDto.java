package com.mjkam.search.api.controller.dto;

import com.mjkam.search.api.domain.KeywordCount;
import lombok.Getter;

@Getter
public class KeywordCountDto {
    private final String keyword;
    private final Long count;

    public KeywordCountDto(KeywordCount keywordCount) {
        this.keyword = keywordCount.getKeyword();
        this.count = keywordCount.getCount();
    }
}
