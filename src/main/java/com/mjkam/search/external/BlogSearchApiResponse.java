package com.mjkam.search.external;

import com.mjkam.search.external.provider.kakao.KakaoApiResponse;

import java.util.List;
import java.util.stream.Collectors;

public class BlogSearchApiResponse {
    private final int totalCount;
    private final int pageableCount;
    private final List<BlogInfo> documents;

    public BlogSearchApiResponse(int totalCount, int pageableCount, List<BlogInfo> blogInfos) {
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
}
