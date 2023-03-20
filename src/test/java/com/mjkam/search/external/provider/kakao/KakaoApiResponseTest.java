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
                    "10:10:100:100:10",
                    "9:7:1000:100:7",
                    "15:9:100:100:0",
                    "2:7:13:13:6"
            })
    void splitResponseDocumentsByPageAndSize(
            int page, int size, int totalCountFromServer, int pageableCountFromServer, int expectItemCount) {

        KakaoApiResponse kakaoApiResponse =
                KakaoApiResponseCreator.createKakaoResponse(totalCountFromServer, pageableCountFromServer, page, size);

        //when
        List<KakaoDocument> result = kakaoApiResponse.getDocumentsForResponse(page, size);

        //then
        assertThat(result.size()).isEqualTo(expectItemCount);
    }
}
