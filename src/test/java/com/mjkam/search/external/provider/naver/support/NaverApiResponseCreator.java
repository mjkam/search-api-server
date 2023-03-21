package com.mjkam.search.external.provider.naver.support;

import com.mjkam.search.external.provider.naver.NaverResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NaverApiResponseCreator {
//    public static BlogSearchApiResponse createApiResponse(int totalCount, int pageableCount, int page, int size) {
//        return BlogSearchApiResponse.fromKakao(page, size, createKakaoResponse(totalCount, pageableCount, page, size));
//    }

    public static NaverResponse create(int totalCount, int page, int size) {
        List<NaverResponse.Item> items = createItems(totalCount, page, size);
        return NaverApiResponseBuilder.naverApiResponse()
                .total(totalCount)
                .start((page - 1) * size + 1)
                .display(items.size())
                .items(items)
                .build();
    }

    // naver api 가 리턴하는 방식
    private static List<NaverResponse.Item> createItems(int totalCount, int page, int size) {
        int pageableCount = Math.min(totalCount, 200);
        int totalPageCount = pageableCount / size;
        if (pageableCount % size != 0) {
            totalPageCount++;
        }
        if (page < totalPageCount) {
            return items(size);
        } else if (page == totalPageCount) {
            if (pageableCount % size == 0) {
                return items(size);
            } else {
                return items(pageableCount % size);
            }
        } else {
            return List.of();
        }
    }

    private static List<NaverResponse.Item> items(int count) {
        NaverResponse.Item dummy = NaverItemBuilder.naverItem().build();

        return IntStream.range(0, count)
                .mapToObj(i -> dummy)
                .collect(Collectors.toList());
    }
}
