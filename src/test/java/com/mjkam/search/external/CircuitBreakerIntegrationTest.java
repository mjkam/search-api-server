package com.mjkam.search.external;

import com.mjkam.search.external.provider.ClientResponse;
import com.mjkam.search.external.provider.ProviderClient;
import com.mjkam.search.external.provider.ProviderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
public class CircuitBreakerIntegrationTest extends ExternalBaseTest{
    @Autowired
    private BlogSearchExternalService sut;

    @MockBean
    private ProviderClientManager requesterManager;
    @MockBean
    private ProviderCircuitConfiguration requesterConfiguration;

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

    private static class MockExceptionApiRequester implements ProviderClient {
        @Override
        public ClientResponse execute(BlogSearchRequest request) {
            throw new RuntimeException("MockExceptionApiRequester Exception");
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return false;
        }
    }

    private static class MockSuccessApiRequester implements ProviderClient {
        private BlogSearchRequest received;

        @Override
        public ClientResponse execute(BlogSearchRequest request) {
            received = request;
            return new ClientResponse();
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
