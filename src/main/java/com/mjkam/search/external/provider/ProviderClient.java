package com.mjkam.search.external.provider;

import com.mjkam.search.external.BlogSearchRequest;

public interface ProviderClient {
    ClientResponse execute(BlogSearchRequest request);
    boolean isMatch(ProviderType providerType);
}
