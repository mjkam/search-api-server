package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderClient;
import com.mjkam.search.external.provider.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProviderClientManager {
    private final List<ProviderClient> requesters;

    public ProviderClient get(ProviderType providerType) {
        return requesters.stream()
                .filter(requester -> requester.isMatch(providerType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Can not find requester of ProviderType " + providerType));
    }
}
