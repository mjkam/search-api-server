package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
public class CircuitBreakerIntegrationTest extends ExternalBaseTest{
    @Autowired
    private BlogSearchRequestService sut;

    @MockBean
    private BlogSearchApiRequesterManager requesterManager;
    @MockBean
    private BlogSearchApiRequesterConfiguration requesterConfiguration;

    private MockExceptionApiRequester exceptionApiRequester;
    private MockSuccessApiRequester successApiRequester;

    @BeforeEach
    void setup() {
        exceptionApiRequester = new MockExceptionApiRequester();
        successApiRequester = new MockSuccessApiRequester();

        given(requesterConfiguration.getMain()).willReturn(ProviderType.KAKAO);
        given(requesterConfiguration.getMain()).willReturn(ProviderType.NAVER);
        given(requesterManager.get(ProviderType.KAKAO)).willReturn(exceptionApiRequester);
        given(requesterManager.get(ProviderType.NAVER)).willReturn(successApiRequester);
    }

    @Test
    @DisplayName("메인 provider api 가 실패하면 fallback 으로 서브 provider api 호출")
    void callSubProviderApi_whenMainProviderApiFailed() {
        //given
        BlogSearchRequest request = new BlogSearchRequest(DUMMY_QUERY, 1, 1, DUMMY_SORTING_TYPE);

        //when
        sut.search(request);

        //then
        assertThat(successApiRequester.getReceived()).isEqualTo(request);
    }

    private static class MockExceptionApiRequester implements BlogSearchApiRequester {
        @Override
        public BlogSearchApiResponse execute(BlogSearchRequest request) {
            throw new RuntimeException("MockExceptionApiRequester Exception");
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return false;
        }
    }

    private static class MockSuccessApiRequester implements BlogSearchApiRequester {
        private BlogSearchRequest received;

        @Override
        public BlogSearchApiResponse execute(BlogSearchRequest request) {
            received = request;
            return new BlogSearchApiResponse();
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return false;
        }

        public BlogSearchRequest getReceived() {
            return received;
        }
    }
}
