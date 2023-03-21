package com.mjkam.search.external.provider.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjkam.search.external.provider.BlogDto;
import com.mjkam.search.external.provider.ClientResponse;
import com.mjkam.search.external.provider.ProviderBlogInfo;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NaverResponse implements ClientResponse {
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;
    private String lastBuildDate;

    @JsonIgnore
    @Override
    public int getTotalCount() {
        return total;
    }

    @JsonIgnore
    @Override
    public int getPageableCount() {
        return Math.min(total, 200);
    }

    @JsonIgnore
    @Override
    public List<BlogDto> getDocuments() {
        return items.stream()
                .map(BlogDto::of)
                .collect(Collectors.toList());
    }

    public static class Item implements ProviderBlogInfo {
        @JsonProperty("title")
        private String title;
        @JsonProperty("link")
        private String link;
        @JsonProperty("description")
        private String description;
        @JsonProperty("bloggername")
        private String bloggerName;
        @JsonProperty("bloggerlink")
        private String bloggerLink;
        @JsonProperty("postdate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        private LocalDate postDate;

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public String getBlogName() {
            return this.bloggerName;
        }

        @Override
        public String getContents() {
            return this.description;
        }

        @Override
        public String getUrl() {
            return this.link;
        }

        @Override
        public String getThumbnail() {
            return "";
        }

        @Override
        public LocalDateTime getPostingDateTime() {
            return this.postDate.atStartOfDay();
        }
    }
}