package com.mjkam.search.external.provider;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BlogDto {
    private final String title;
    private final String blogName;
    private final String contents;
    private final String url;
    private final String thumbnail;
    private final LocalDateTime postDate;

    public BlogDto(String title, String blogName, String contents, String url, String thumbnail, LocalDateTime postDate) {
        this.title = title;
        this.blogName = blogName;
        this.contents = contents;
        this.url = url;
        this.thumbnail = thumbnail;
        this.postDate = postDate;
    }

    public static BlogDto of(ProviderBlogInfo providerBlogInfo) {
        return new BlogDto(
                providerBlogInfo.getTitle(),
                providerBlogInfo.getBlogName(),
                providerBlogInfo.getContents(),
                providerBlogInfo.getUrl(),
                providerBlogInfo.getThumbnail(),
                providerBlogInfo.getPostingDateTime());
    }
}
