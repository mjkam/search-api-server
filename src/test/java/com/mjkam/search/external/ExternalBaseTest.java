package com.mjkam.search.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ExternalBaseTest {
    protected final ObjectMapper objectMapper;
    protected final String DUMMY_KAKAO_API_KEY = "DUMMY";
    protected final String DUMMY_NAVER_CLIENT_ID = "DUMMY";
    protected final String DUMMY_NAVER_CLIENT_SECRET = "DUMMY";
    protected final String DUMMY_API_ENDPOINT = "http://test.com";
    protected final String DUMMY_QUERY = "TEST";
    protected final SortingType DUMMY_SORTING_TYPE = SortingType.ACCURACY;

    {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
