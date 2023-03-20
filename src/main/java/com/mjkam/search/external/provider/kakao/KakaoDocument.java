package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjkam.search.external.provider.ProviderBlogInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

public class KakaoDocument implements ProviderBlogInfo {
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
