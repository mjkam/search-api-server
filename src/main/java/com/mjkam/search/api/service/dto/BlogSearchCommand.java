package com.mjkam.search.api.service.dto;

import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.SortingType;
import lombok.Getter;

public class BlogSearchCommand {
    @Getter
    private final String query;
    private final Integer page;
    private final Integer size;
    private final String sort;

    public BlogSearchCommand(String query, Integer page, Integer size, String sort) {
        this.query = query;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public BlogSearchRequest toSearchRequest() {
        return new BlogSearchRequest(query, page, size, SortingType.valueOf(sort.toUpperCase()));
    }
}
