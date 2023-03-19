package com.mjkam.search.external;

public class BlogSearchRequest {
    private final String query;
    private final Integer page;
    private final Integer size;
    private final SortingType sortingType;

    public BlogSearchRequest(String query, Integer page, Integer size, SortingType sortingType) {
        this.query = query;
        this.page = page;
        this.size = size;
        this.sortingType = sortingType;
    }
}
