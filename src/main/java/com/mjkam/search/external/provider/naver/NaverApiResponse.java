package com.mjkam.search.external.provider.naver;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverApiResponse {
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverItem> items;
    private String lastBuildDate;
}