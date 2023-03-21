package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjkam.search.external.provider.ProviderBlogInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class KakaoResponse {
    @JsonProperty("meta")
    private Meta meta;
    private List<Document> documents;

    public int getTotalCount() {
        return this.meta.getTotalCount();
    }

    public int getPageableCount() {
        return this.meta.getPageableCount();
    }

    public List<Document> getDocumentsForResponse(int page, int size) {
        int totalPageNum = meta.getPageableCount() / size;
        if (meta.getPageableCount() % size != 0) {
            totalPageNum++;
        }

        if (page > totalPageNum) {
            return List.of();
        }
        return documents;
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