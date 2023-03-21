package com.mjkam.search.external.provider;

import com.mjkam.search.external.provider.kakao.KakaoResponse;
import com.mjkam.search.external.provider.naver.NaverResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ClientResponse {
    private int totalCount;
    private int pageableCount;
    private List<BlogDto> documents;

    private ClientResponse(int totalCount, int pageableCount, List<BlogDto> blogDtos) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.documents = blogDtos;
    }

    public static ClientResponse fromKakao(int page, int size, KakaoResponse response) {
        List<BlogDto> blogDocuments = response.getDocumentsForResponse(page, size).stream()
                .map(BlogDto::of)
                .collect(Collectors.toList());

        return new ClientResponse(
                response.getTotalCount(), response.getPageableCount(), blogDocuments);
    }

    public static ClientResponse fromNaver(NaverResponse response) {
        List<BlogDto> blogDtos = response.getItems().stream()
                .map(BlogDto::of)
                .collect(Collectors.toList());

        return new ClientResponse(response.getTotal(), Math.min(response.getTotal(), 200), blogDtos);
    }
}
