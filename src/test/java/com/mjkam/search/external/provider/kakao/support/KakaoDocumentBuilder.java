package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.kakao.KakaoResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public final class KakaoDocumentBuilder {
    private String title = "";
    private String contents = "";
    private String url = "";
    private String blogName = "";
    private String thumbnail = "";
    private LocalDateTime datetime = LocalDateTime.now();

    private KakaoDocumentBuilder() {
    }

    public static KakaoDocumentBuilder kakaoDocument() {
        return new KakaoDocumentBuilder();
    }

    public KakaoDocumentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public KakaoDocumentBuilder contents(String contents) {
        this.contents = contents;
        return this;
    }

    public KakaoDocumentBuilder url(String url) {
        this.url = url;
        return this;
    }

    public KakaoDocumentBuilder blogName(String blogName) {
        this.blogName = blogName;
        return this;
    }

    public KakaoDocumentBuilder thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public KakaoDocumentBuilder datetime(LocalDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public KakaoResponse.Document build() {
        KakaoResponse.Document document = new KakaoResponse.Document();
        ReflectionTestUtils.setField(document, "title", title);
        ReflectionTestUtils.setField(document, "contents", contents);
        ReflectionTestUtils.setField(document, "url", url);
        ReflectionTestUtils.setField(document, "blogName", blogName);
        ReflectionTestUtils.setField(document, "thumbnail", thumbnail);
        ReflectionTestUtils.setField(document, "datetime", datetime);

        return document;
    }
}
