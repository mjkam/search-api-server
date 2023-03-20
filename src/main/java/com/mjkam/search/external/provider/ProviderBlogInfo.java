package com.mjkam.search.external.provider;

import java.time.LocalDateTime;

public interface ProviderBlogInfo {
    String getTitle();
    String getBlogName();
    String getContents();
    String getUrl();
    String getThumbnail();
    LocalDateTime getPostingDateTime();
}