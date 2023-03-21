package com.mjkam.search.external.provider;

import lombok.Getter;

@Getter
public enum SortingType {
    ACCURACY("accuracy", "sim"),
    RECENCY("recency", "date"),
    ;

    private final String kakaoName;
    private final String naverName;

    SortingType(String kakaoName, String naverName) {
        this.kakaoName = kakaoName;
        this.naverName = naverName;
    }
}
