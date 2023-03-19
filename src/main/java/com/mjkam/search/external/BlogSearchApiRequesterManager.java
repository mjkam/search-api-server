package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogSearchApiRequesterManager {
    private final List<BlogSearchApiRequester> requesters;

    public BlogSearchApiRequester get(ProviderType providerType) {
        return requesters.stream()
                .filter(requester -> requester.isMatch(providerType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Can not find requester of ProviderType " + providerType));
    }
}
