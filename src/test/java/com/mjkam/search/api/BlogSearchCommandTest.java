package com.mjkam.search.api;

import com.mjkam.search.api.exception.ApiException;
import com.mjkam.search.api.service.dto.BlogSearchCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlogSearchCommandTest {
    @ParameterizedTest
    @CsvSource(
            {"https://abc.com search,search",
                    "http://kakao.com MyKeyWord,MyKeyWord",
                    "http principal,http principal",
                    "https security,https security",
                    "http://kakao.com MyKeyWord Kakao,MyKeyWord Kakao"})
    @DisplayName("sort 값은 정해진 값만 가능")
    void extractKeyword_whenStartWithUrl(String query, String keyword) {
        BlogSearchCommand blogSearchCommand = new BlogSearchCommand(query, "1", "1", "accuracy");
        assertThat(blogSearchCommand.getKeywordFromQuery()).isEqualTo(keyword);
    }

    @ParameterizedTest
    @ValueSource(strings = {"accuracy", "recency"})
    @DisplayName("sort 값은 정해진 값만 가능")
    void success_whenSortIsAccuracyOrRecency(String sort) {
        new BlogSearchCommand("query", "1", "1", sort);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "accu", "", "recen"})
    @DisplayName("sort 값이 정해진 값이 아니면 예외 발생")
    void throwException_whenInvalidSort(String sort) {
        //when then
        assertThatThrownBy(() -> new BlogSearchCommand("query", "1", "1", sort))
                .isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("size 가 1 ~ 50 이어야 함")
    void success_whenSizeInSuccessRange() {
        //when then
        for (int size = 1; size <= 50; size++) {
            new BlogSearchCommand("query", "1", String.valueOf(size), "accuracy");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "51"})
    @DisplayName("size 가 1 ~ 50 범위 밖이면 예외 발생")
    void throwException_whenSizeOutOfRange(String size) {
        //when then
        assertThatThrownBy(() -> new BlogSearchCommand("query", "1", size, "accuracy"))
                .isInstanceOf(ApiException.class);

    }

    @Test
    @DisplayName("size 가 Integer 로 변환이 불가능하면 예외 발생")
    void throwException_whenSizeCanNotBeInteger() {
        String size = "a11";
        //when then
        assertThatThrownBy(() -> new BlogSearchCommand("query", "1", size, "accuracy"))
                .isInstanceOf(ApiException.class);

    }

    @Test
    @DisplayName("page 가 1 ~ 50 이어야 함")
    void success_whenPageInSuccessRange() {
        //when then
        for (int page = 1; page <= 50; page++) {
            new BlogSearchCommand("query", String.valueOf(page), "1", "accuracy");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "51"})
    @DisplayName("page 가 1 ~ 50 범위 밖이면 예외 발생")
    void throwException_whenPageOutOfRange(String page) {
        //when then
        assertThatThrownBy(() -> new BlogSearchCommand("query", page, "1", "accuracy"))
                .isInstanceOf(ApiException.class);

    }

    @Test
    @DisplayName("page 가 Integer 로 변환이 불가능하면 예외 발생")
    void throwException_whenPageIsString() {
        String page = "a11";
        //when then
        assertThatThrownBy(() -> new BlogSearchCommand("query", page, "1", "accuracy"))
                .isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("query 길이가 1 ~ 255 이어야 함")
    void success_whenQueryIsInNormalRange() {
        //when then
        String s = "";
        for (int i = 1; i <= 255; i++) {
            s += "0";
            new BlogSearchCommand(s, "1", "1", "accuracy");
        }
    }

    @ParameterizedTest
    @MethodSource("provideQueryParamExceptionCases")
    @DisplayName("query 값이 null, 빈문자열, 길이 255 초과하면 예외 발생")
    void throwException_whenQueryExceedMax(String query) {
        assertThatThrownBy(() -> new BlogSearchCommand(query, "1", "1", "accuracy"))
                .isInstanceOf(ApiException.class);
    }

    private static Stream<String> provideQueryParamExceptionCases() {
        StringBuilder lengthOf256 = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            lengthOf256.append("0");
        }
        return Stream.of(null, "", lengthOf256.toString());
    }


}

