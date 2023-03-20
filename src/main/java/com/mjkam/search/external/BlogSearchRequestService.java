package com.mjkam.search.external;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogSearchRequestService {
    private final BlogSearchApiRequesterConfiguration requesterConfiguration;
    private final BlogSearchApiRequesterManager requesterManager;

    @CircuitBreaker(name = "search", fallbackMethod = "searchFallBack")
    public BlogSearchApiResponse search(BlogSearchRequest request) {
        BlogSearchApiRequester blogSearchApiRequester = requesterManager.get(requesterConfiguration.getMain());
        return blogSearchApiRequester.execute(request);
    }

    private BlogSearchApiResponse searchFallBack(BlogSearchRequest request, Exception e) {
        BlogSearchApiRequester blogSearchApiRequester = requesterManager.get(requesterConfiguration.getFallback());
        return blogSearchApiRequester.execute(request);
    }
}
