package com.mjkam.search.external.provider.kakao.support;

import com.mjkam.search.external.provider.kakao.KakaoDocument;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DummyKakaoDocumentCreator {
    // kakao api 가 리턴하는 방식
    public static List<KakaoDocument> create(int pageableCount, int page, int size) {
        if (pageableCount == 0) {
            return List.of();
        }
        if (pageableCount % size == 0) {
            return documents(size);
        } else {
            if (page * size > pageableCount) {
                return documents(pageableCount % size);
            } else {
                return documents(size);
            }
        }
    }

    private static List<KakaoDocument> documents(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new KakaoDocument())
                .collect(Collectors.toList());
    }
}
