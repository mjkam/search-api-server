package com.mjkam.search.api.controller.dto;

import com.mjkam.search.external.BlogInfo;
import com.mjkam.search.external.BlogSearchApiResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BlogSearchResponse {
    private final int totalCount;
    private final int pageableCount;
    private final List<BlogInfoDto> documents;

    public BlogSearchResponse(BlogSearchApiResponse apiResponse) {
        this.totalCount = apiResponse.getTotalCount();
        this.pageableCount = apiResponse.getPageableCount();
        this.documents = apiResponse.getDocuments().stream()
                .map(e -> new BlogInfoDto(e))
                .collect(Collectors.toList());
    }

    @Getter
    static class BlogInfoDto {
        private final String title;
        private final String blogName;
        private final String contents;
        private final String url;
        private final String thumbnail;
        private final LocalDateTime postDate;

        public BlogInfoDto(BlogInfo blogInfo) {
            this.title = blogInfo.getTitle();
            this.blogName = blogInfo.getBlogName();
            this.contents = blogInfo.getContents();
            this.url = blogInfo.getUrl();
            this.thumbnail = blogInfo.getThumbnail();
            this.postDate = blogInfo.getPostDate();
        }
    }
}
