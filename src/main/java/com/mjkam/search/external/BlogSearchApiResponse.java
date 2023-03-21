package com.mjkam.search.external;

import com.mjkam.search.external.provider.kakao.KakaoApiResponse;
import com.mjkam.search.external.provider.naver.NaverApiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class BlogSearchApiResponse {
    private int totalCount;
    private int pageableCount;
    private List<BlogInfo> documents;

    private BlogSearchApiResponse(int totalCount, int pageableCount, List<BlogInfo> blogInfos) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.documents = blogInfos;
    }

    public static BlogSearchApiResponse fromKakao(int page, int size, KakaoApiResponse response) {
        List<BlogInfo> blogDocuments = response.getDocumentsForResponse(page, size).stream()
                .map(BlogInfo::of)
                .collect(Collectors.toList());

        return new BlogSearchApiResponse(
                response.getTotalCount(), response.getPageableCount(), blogDocuments);
    }

    public static BlogSearchApiResponse fromNaver(NaverApiResponse response) {
        List<BlogInfo> blogInfos = response.getItems().stream()
                .map(BlogInfo::of)
                .collect(Collectors.toList());

        return new BlogSearchApiResponse(response.getTotal(), Math.min(response.getTotal(), 200), blogInfos);
    }
}
