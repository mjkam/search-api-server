package com.mjkam.search.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogSearchRequestService {
    private final BlogSearchApiRequesterConfiguration requesterConfiguration;
    private final BlogSearchApiRequesterManager requesterManager;

    public BlogSearchApiResponse search(BlogSearchRequest request) {
        BlogSearchApiRequester blogSearchApiRequester = requesterManager.get(requesterConfiguration.getMain());
        return blogSearchApiRequester.execute(request);
    }
}
