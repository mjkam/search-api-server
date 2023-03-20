package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderBlogInfo;
import com.mjkam.search.external.provider.kakao.KakaoDocument;
import com.mjkam.search.external.provider.naver.NaverItem;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BlogInfo {
    private final String title;
    private final String blogName;
    private final String contents;
    private final String url;
    private final String thumbnail;
    private final LocalDateTime postDate;

    public BlogInfo(String title, String blogName, String contents, String url, String thumbnail, LocalDateTime postDate) {
        this.title = title;
        this.blogName = blogName;
        this.contents = contents;
        this.url = url;
        this.thumbnail = thumbnail;
        this.postDate = postDate;
    }

    public static BlogInfo of(ProviderBlogInfo providerBlogInfo) {
        return new BlogInfo(
                providerBlogInfo.getTitle(),
                providerBlogInfo.getBlogName(),
                providerBlogInfo.getContents(),
                providerBlogInfo.getUrl(),
                providerBlogInfo.getThumbnail(),
                providerBlogInfo.getPostingDateTime());
    }
}
