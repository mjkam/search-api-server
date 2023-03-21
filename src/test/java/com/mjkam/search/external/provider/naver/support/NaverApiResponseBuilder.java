package com.mjkam.search.external.provider.naver.support;

import com.mjkam.search.external.provider.naver.NaverResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public final class NaverApiResponseBuilder {
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverResponse.Item> items;
    private String lastBuildDate = "";

    private NaverApiResponseBuilder() {
    }

    public static NaverApiResponseBuilder naverApiResponse() {
        return new NaverApiResponseBuilder();
    }

    public NaverApiResponseBuilder total(Integer total) {
        this.total = total;
        return this;
    }

    public NaverApiResponseBuilder start(Integer start) {
        this.start = start;
        return this;
    }

    public NaverApiResponseBuilder display(Integer display) {
        this.display = display;
        return this;
    }

    public NaverApiResponseBuilder items(List<NaverResponse.Item> items) {
        this.items = items;
        return this;
    }

    public NaverApiResponseBuilder lastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
        return this;
    }

    public NaverResponse build() {
        NaverResponse response = new NaverResponse();
        ReflectionTestUtils.setField(response, "total", total);
        ReflectionTestUtils.setField(response, "start", start);
        ReflectionTestUtils.setField(response, "display", display);
        ReflectionTestUtils.setField(response, "items", items);
        ReflectionTestUtils.setField(response, "lastBuildDate", lastBuildDate);

        return response;
    }
}
