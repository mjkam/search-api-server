package com.mjkam.search.external;

import lombok.Getter;

@Getter
public enum SortingType {
    ACCURACY("accuracy"),
    RECENCY("recency"),
    ;

    private final String kakaoName;

    SortingType(String kakaoName) {
        this.kakaoName = kakaoName;
    }
}
