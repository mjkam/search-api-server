package com.mjkam.search.external.provider.kakao;

import com.mjkam.search.external.provider.kakao.builder.KakaoApiResponseBuilder;
import com.mjkam.search.external.provider.kakao.builder.KakaoDocumentBuilder;
import com.mjkam.search.external.provider.kakao.builder.KakaoMetaBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mjkam.search.external.provider.kakao.builder.KakaoApiResponseBuilder.*;
import static com.mjkam.search.external.provider.kakao.builder.KakaoMetaBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class KakaoApiResponseTest {
    @ParameterizedTest
    @DisplayName("KakaoApiResponse 는 요청 page 와 size 에 맞는 개수로 잘라서 Document 를 리턴해야함")
    @CsvSource(delimiter = ':',
            value = {
                    "100:10:10:10",
                    "100:9:7:7",
                    "100:15:9:0",
                    "13:2:7:6"})
    void splitResponseDocumentsByPageAndSize(int pageableCount, int page, int size, int expect) {
        //given
        KakaoMeta meta =
                kakaoMeta()
                        .pageableCount(pageableCount)
                        .build();
        List<KakaoDocument> kakaoDocuments = createKakaoDocuments(pageableCount, page, size);

        KakaoApiResponse kakaoApiResponse =
                kakaoApiResponse()
                        .kakaoMeta(meta)
                        .documents(kakaoDocuments)
                        .build();

        //when
        List<KakaoDocument> result = kakaoApiResponse.getDocumentsForResponse(page, size);

        //then
        assertThat(result.size()).isEqualTo(expect);
    }

    // kakao api 가 리턴하는 방식
    private List<KakaoDocument> createKakaoDocuments(int pageableCount, int page, int size) {
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

    private List<KakaoDocument> documents(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new KakaoDocument())
                .collect(Collectors.toList());
    }
}
