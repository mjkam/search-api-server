package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class KakaoDocument {
    private String title;
    private String contents;
    private String url;
    @JsonProperty("blogname")
    private String blogName;
    private String thumbnail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime datetime;
}
