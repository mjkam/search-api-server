package com.mjkam.search.external;

import com.mjkam.search.external.provider.ClientResponse;
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
    public ClientResponse search(BlogSearchRequest request) {
        ProviderClient providerClient = requesterManager.get(requesterConfiguration.getMain());
        return providerClient.execute(request);
    }

    private ClientResponse searchFallBack(BlogSearchRequest request, Exception e) {
        ProviderClient providerClient = requesterManager.get(requesterConfiguration.getFallback());
        return providerClient.execute(request);
    }
}
