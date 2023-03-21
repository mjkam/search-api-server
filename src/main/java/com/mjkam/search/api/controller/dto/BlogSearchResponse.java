package com.mjkam.search.api.controller.dto;

import com.mjkam.search.external.provider.BlogDto;
import com.mjkam.search.external.provider.ClientResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BlogSearchResponse {
    private final int totalCount;
    private final int pageableCount;
    private final List<BlogInfoDto> documents;

    public BlogSearchResponse(ClientResponse apiResponse) {
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

        public BlogInfoDto(BlogDto blogDto) {
            this.title = blogDto.getTitle();
            this.blogName = blogDto.getBlogName();
            this.contents = blogDto.getContents();
            this.url = blogDto.getUrl();
            this.thumbnail = blogDto.getThumbnail();
            this.postDate = blogDto.getPostDate();
        }
    }
}
