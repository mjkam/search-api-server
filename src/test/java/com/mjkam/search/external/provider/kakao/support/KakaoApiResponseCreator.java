package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.ClientResponse;
import com.mjkam.search.external.provider.kakao.KakaoResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mjkam.search.external.provider.kakao.support.KakaoApiResponseBuilder.kakaoApiResponse;
import static com.mjkam.search.external.provider.kakao.support.KakaoMetaBuilder.kakaoMeta;

public class KakaoApiResponseCreator {

    public static ClientResponse createApiResponse(int totalCount, int pageableCount, int page, int size) {
        return ClientResponse.fromKakao(page, size, createKakaoResponse(totalCount, pageableCount, page, size));
    }

    public static KakaoResponse createKakaoResponse(int totalCount, int pageableCount, int page, int size) {
        KakaoResponse.Meta meta =
                kakaoMeta()
                        .totalCount(totalCount)
                        .pageableCount(pageableCount)
                        .build();
        List<KakaoResponse.Document> documents = createDocuments(pageableCount, page, size);

        return kakaoApiResponse()
                .kakaoMeta(meta)
                .documents(documents)
                .build();
    }

    // kakao api 가 리턴하는 방식
    private static List<KakaoResponse.Document> createDocuments(int pageableCount, int page, int size) {
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

    private static List<KakaoResponse.Document> documents(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new KakaoResponse.Document())
                .collect(Collectors.toList());
    }
}
