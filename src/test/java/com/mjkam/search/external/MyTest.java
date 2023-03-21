package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderType;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class MyTest {
    private BlogSearchRequestService sut;

    private BlogSearchApiRequesterConfiguration requesterConfiguration;

    private BlogSearchApiRequester mainRequester;
    private BlogSearchApiRequester subRequester;

    private BlogSearchApiResponse mainReturnObj;
    private BlogSearchApiResponse fallbackReturnObj;

    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setup() {
        mainReturnObj = new BlogSearchApiResponse();
        fallbackReturnObj = new BlogSearchApiResponse();
        mainRequester = new MockExceptionApiRequester(ProviderType.KAKAO, 5);
        subRequester = new MockApiRequester(ProviderType.NAVER, fallbackReturnObj);

        requesterConfiguration = new BlogSearchApiRequesterConfiguration();
        requesterConfiguration.setMain(ProviderType.KAKAO);
        requesterConfiguration.setFallback(ProviderType.NAVER);

        BlogSearchApiRequesterManager requesterManager = new BlogSearchApiRequesterManager(List.of(mainRequester, subRequester));

        sut = new BlogSearchRequestService(requesterConfiguration, requesterManager);

        CircuitBreakerConfig circuitBreakerConfig =
                CircuitBreakerConfig.custom()
                        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                        .slidingWindowSize(10)
                        .minimumNumberOfCalls(10)
                        .failureRateThreshold(100)
                        .slowCallDurationThreshold(Duration.ofSeconds(10))
                        .slowCallRateThreshold(100)
                        .permittedNumberOfCallsInHalfOpenState(10)
                        .maxWaitDurationInHalfOpenState(Duration.ofSeconds(100)) // ?
                        .waitDurationInOpenState(Duration.ofSeconds(100))
                        .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        circuitBreaker = circuitBreakerRegistry.circuitBreaker("test");
    }

    @Test
    void test() {
        //given
        BlogSearchRequest request = new BlogSearchRequest("", 1, 1, SortingType.ACCURACY);

        for (int i = 0; i < 20; i++) {
            try {
                BlogSearchApiResponse blogSearchApiResponse =
                        circuitBreaker.decorateSupplier(() -> sut.search(request)).get();
            } catch (Exception e) {
                System.out.println(circuitBreaker.getState());
            }
        }
    }

    private static class MockExceptionApiRequester implements BlogSearchApiRequester {
        private final ProviderType providerType;
        private final int maxFailCount;
        private int count;

        public MockExceptionApiRequester(ProviderType providerType, int maxFailCount) {
            this.providerType = providerType;
            this.maxFailCount = maxFailCount;
        }

        @Override
        public BlogSearchApiResponse execute(BlogSearchRequest request) {
            if (count >= maxFailCount) {
                return new BlogSearchApiResponse();
            } else {
                count++;
                throw new RuntimeException();
            }
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return this.providerType.equals(providerType);
        }
    }

    private static class MockApiRequester implements BlogSearchApiRequester {
        private final Object data;
        private final ProviderType providerType;

        public MockApiRequester(ProviderType providerType, Object object) {
            this.providerType = providerType;
            this.data = object;
        }

        @Override
        public BlogSearchApiResponse execute(BlogSearchRequest request) {
            return new BlogSearchApiResponse();
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return this.providerType.equals(providerType);
        }
    }
}
