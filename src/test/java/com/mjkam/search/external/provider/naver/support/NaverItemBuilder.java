package com.mjkam.search.external.provider.naver.support;

import com.mjkam.search.external.provider.naver.NaverItem;
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

    public NaverItem build() {
        NaverItem naverItem = new NaverItem();
        ReflectionTestUtils.setField(naverItem, "title", title);
        ReflectionTestUtils.setField(naverItem, "link", link);
        ReflectionTestUtils.setField(naverItem, "description", description);
        ReflectionTestUtils.setField(naverItem, "bloggerName", bloggerName);
        ReflectionTestUtils.setField(naverItem, "bloggerLink", bloggerLink);
        ReflectionTestUtils.setField(naverItem, "postDate", postDate);

        return naverItem;
    }
}
