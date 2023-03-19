package com.mjkam.search.external;

import java.util.List;

public class BlogSearchApiResponse {
    private final int totalCount;
    private final int pageableCount;
    private final List<BlogInfo> documents;

    public BlogSearchApiResponse(int totalCount, int pageableCount, List<BlogInfo> blogInfos) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.documents = blogInfos;
    }
}
