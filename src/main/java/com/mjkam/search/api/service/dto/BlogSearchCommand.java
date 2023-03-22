package com.mjkam.search.api.service.dto;

import com.mjkam.search.api.exception.ApiException;
import com.mjkam.search.api.exception.ErrorType;
import com.mjkam.search.external.BlogSearchRequest;
import com.mjkam.search.external.provider.SortingType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BlogSearchCommand {
    private final String query;
    private final int page;
    private final int size;
    private final String sort;

    public BlogSearchCommand(String query, String page, String size, String sort) {
        if (query == null || query.equals("") || query.length() > 255) {
            throw new ApiException(ErrorType.INVALID_QUERY_PARAM_ERROR, "received query: " + query);
        }
        if (!page.chars().allMatch(Character::isDigit) || Integer.parseInt(page) < 1 || Integer.parseInt(page) > 50) {
            throw new ApiException(ErrorType.INVALID_PAGE_PARAM_ERROR, "received page: " + page);
        }
        if (!size.chars().allMatch(Character::isDigit) || Integer.parseInt(size) < 1 || Integer.parseInt(size) > 50) {
            throw new ApiException(ErrorType.INVALID_SIZE_PARAM_ERROR, "received size: " + size);
        }
        if (!sort.equals("accuracy") && !sort.equals("recency")) {
            throw new ApiException(ErrorType.INVALID_SORT_PARAM_ERROR, "received sort: " + sort);
        }
        this.query = query;
        this.page = Integer.parseInt(page);
        this.size = Integer.parseInt(size);
        this.sort = sort;
    }

    public String getKeywordFromQuery() {
        if ((query.startsWith("http://") || query.startsWith("https://")) && query.split(" ").length >= 2) {
            return Arrays.stream(query.split(" ")).skip(1).collect(Collectors.joining(" "));
        }
        return query;
    }

    public BlogSearchRequest toSearchRequest() {
        return new BlogSearchRequest(query, page, size, SortingType.valueOf(sort.toUpperCase()));
    }
}
