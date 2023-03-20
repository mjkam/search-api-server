package com.mjkam.search.external.provider.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NaverItem {
    private String title;
    private String link;
    private String description;
    @JsonProperty("bloggername")
    private String bloggerName;
    @JsonProperty("bloggerlink")
    private String bloggerLink;
    @JsonProperty("postdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate postDate;

}