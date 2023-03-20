package com.mjkam.search.external.provider.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjkam.search.external.BlogInfo;
import com.mjkam.search.external.provider.ProviderBlogInfo;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NaverItem implements ProviderBlogInfo {
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