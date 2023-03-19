package com.mjkam.search.external.provider.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoMeta {
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonProperty("pageable_count")
    private Integer pageableCount;
    @JsonProperty("is_end")
    private Boolean isEnd;
}
