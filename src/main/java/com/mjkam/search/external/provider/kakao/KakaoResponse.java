package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjkam.search.external.provider.BlogDto;
import com.mjkam.search.external.provider.ClientResponse;
import com.mjkam.search.external.provider.ProviderBlogInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@ToString
public class KakaoResponse implements ClientResponse {
    @JsonProperty("meta")
    private Meta meta;
    private List<Document> documents;

    @JsonIgnore
    private int requestedPage;
    @JsonIgnore
    private int requestedSize;

    public void setRequestedPage(int page) {
        this.requestedPage = page;
    }

    public void setRequestedSize(int size) {
        this.requestedSize = size;
    }

    public int getTotalCount() {
        return this.meta.getTotalCount();
    }

    public int getPageableCount() {
        return this.meta.getPageableCount();
    }

    @Override
    public List<BlogDto> getDocuments() {
        if (requestedPage == 0 || requestedSize == 0) {
            throw new IllegalStateException("RequestedPage and RequestedSize Should be Set");
        }

        int totalPageNum = meta.getPageableCount() / requestedSize;
        if (meta.getPageableCount() % requestedSize != 0) {
            totalPageNum++;
        }

        if (requestedPage > totalPageNum) {
            return List.of();
        }
        return documents.stream()
                .map(BlogDto::of)
                .collect(Collectors.toList());
    }

    @Getter
    @ToString
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;
    }

    public static class Document implements ProviderBlogInfo {
        @JsonProperty("title")
        private String title;
        @JsonProperty("contents")
        private String contents;
        @JsonProperty("url")
        private String url;
        @JsonProperty("blogname")
        private String blogName;
        @JsonProperty("thumbnail")
        private String thumbnail;
        @JsonProperty("datetime")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private LocalDateTime dateTime;

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public String getBlogName() {
            return this.blogName;
        }

        @Override
        public String getContents() {
            return this.contents;
        }

        @Override
        public String getUrl() {
            return this.url;
        }

        @Override
        public String getThumbnail() {
            return this.thumbnail;
        }

        @Override
        public LocalDateTime getPostingDateTime() {
            return this.dateTime;
        }
    }
}