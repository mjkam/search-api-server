package com.mjkam.search.external.provider.naver.support;

import com.mjkam.search.external.provider.naver.NaverResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public final class NaverItemBuilder {
    private String title = "";
    private String link = "";
    private String description = "";
    private String bloggerName = "";
    private String bloggerLink = "";
    private LocalDate postDate = LocalDate.now();

    private NaverItemBuilder() {
    }

    public static NaverItemBuilder naverItem() {
        return new NaverItemBuilder();
    }

    public NaverItemBuilder title(String title) {
        this.title = title;
        return this;
    }

    public NaverItemBuilder link(String link) {
        this.link = link;
        return this;
    }

    public NaverItemBuilder description(String description) {
        this.description = description;
        return this;
    }

    public NaverItemBuilder bloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
        return this;
    }

    public NaverItemBuilder bloggerLink(String bloggerLink) {
        this.bloggerLink = bloggerLink;
        return this;
    }

    public NaverItemBuilder postDate(LocalDate postDate) {
        this.postDate = postDate;
        return this;
    }

    public NaverResponse.Item build() {
        NaverResponse.Item item = new NaverResponse.Item();
        ReflectionTestUtils.setField(item, "title", title);
        ReflectionTestUtils.setField(item, "link", link);
        ReflectionTestUtils.setField(item, "description", description);
        ReflectionTestUtils.setField(item, "bloggerName", bloggerName);
        ReflectionTestUtils.setField(item, "bloggerLink", bloggerLink);
        ReflectionTestUtils.setField(item, "postDate", postDate);

        return item;
    }
}
