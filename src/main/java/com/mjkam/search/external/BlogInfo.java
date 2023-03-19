package com.mjkam.search.external;

import com.mjkam.search.external.provider.kakao.KakaoDocument;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

    public static BlogInfo of(KakaoDocument kakaoDocument) {
        return new BlogInfo(
                kakaoDocument.getTitle(),
                kakaoDocument.getBlogName(),
                kakaoDocument.getContents(),
                kakaoDocument.getUrl(),
                kakaoDocument.getThumbnail(),
                kakaoDocument.getDatetime());
    }
}
