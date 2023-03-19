package com.mjkam.search.external.provider.kakao;

import com.mjkam.search.external.provider.kakao.support.KakaoApiResponseCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        KakaoApiResponse kakaoApiResponse = KakaoApiResponseCreator.create(pageableCount, pageableCount, page, size);

        //when
        List<KakaoDocument> result = kakaoApiResponse.getDocumentsForResponse(page, size);

        //then
        assertThat(result.size()).isEqualTo(expect);
    }
}
