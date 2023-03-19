package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderType;

public interface BlogSearchApiRequester {
    BlogSearchApiResponse execute(BlogSearchRequest request);
    boolean isMatch(ProviderType providerType);
}
