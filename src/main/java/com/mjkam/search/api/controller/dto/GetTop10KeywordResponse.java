package com.mjkam.search.api.controller.dto;

import com.mjkam.search.api.domain.KeywordCount;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetTop10KeywordResponse {
    private final List<KeywordCountDto> keywords;
    public GetTop10KeywordResponse(List<KeywordCount> keywordCounts) {
        this.keywords = keywordCounts.stream()
                .map(KeywordCountDto::new)
                .collect(Collectors.toList());
    }
}
