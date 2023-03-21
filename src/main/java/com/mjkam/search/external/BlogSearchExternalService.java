package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogSearchExternalService {
    private final ProviderCircuitConfiguration requesterConfiguration;
    private final ProviderClientManager requesterManager;

    @CircuitBreaker(name = "search", fallbackMethod = "searchFallBack")
    public BlogSearchResultDto search(BlogSearchRequest request) {
        ProviderClient providerClient = requesterManager.get(requesterConfiguration.getMain());
        return BlogSearchResultDto.of(providerClient.execute(request));
    }

    private BlogSearchResultDto searchFallBack(BlogSearchRequest request, Exception e) {
        ProviderClient providerClient = requesterManager.get(requesterConfiguration.getFallback());
        return BlogSearchResultDto.of(providerClient.execute(request));
    }
}
