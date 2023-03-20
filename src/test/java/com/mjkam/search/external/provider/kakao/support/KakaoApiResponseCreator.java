package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.BlogSearchApiResponse;
import com.mjkam.search.external.provider.kakao.KakaoApiResponse;
import com.mjkam.search.external.provider.kakao.KakaoDocument;
import com.mjkam.search.external.provider.kakao.KakaoMeta;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mjkam.search.external.provider.kakao.support.KakaoApiResponseBuilder.kakaoApiResponse;
import static com.mjkam.search.external.provider.kakao.support.KakaoMetaBuilder.kakaoMeta;

public class KakaoApiResponseCreator {

    public static BlogSearchApiResponse createApiResponse(int totalCount, int pageableCount, int page, int size) {
        return BlogSearchApiResponse.fromKakao(page, size, createKakaoResponse(totalCount, pageableCount, page, size));
    }

    public static KakaoApiResponse createKakaoResponse(int totalCount, int pageableCount, int page, int size) {
        KakaoMeta meta =
                kakaoMeta()
                        .totalCount(totalCount)
                        .pageableCount(pageableCount)
                        .build();
        List<KakaoDocument> kakaoDocuments = createDocuments(pageableCount, page, size);

        return kakaoApiResponse()
                .kakaoMeta(meta)
                .documents(kakaoDocuments)
                .build();
    }

    // kakao api 가 리턴하는 방식
    private static List<KakaoDocument> createDocuments(int pageableCount, int page, int size) {
        int totalPageNum = pageableCount / size;
        if (pageableCount % size != 0) {
            totalPageNum++;
        }

        if (pageableCount % size == 0) {
            return documents(size);
        }

        if (page < totalPageNum) {
            return documents(size);
        } else {
            return documents(pageableCount % size);
        }
    }

    private static List<KakaoDocument> documents(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new KakaoDocument())
                .collect(Collectors.toList());
    }
}
