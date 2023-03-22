package com.mjkam.search.api.service;

import com.mjkam.search.api.ranking.WorkDistributor;
import com.mjkam.search.api.service.dto.BlogSearchCommand;
import com.mjkam.search.external.*;
import com.mjkam.search.external.BlogSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogSearchService {
    private final WorkDistributor workDistributor;
    private final BlogSearchExternalService requestService;

    @Transactional
    public BlogSearchResultDto search(BlogSearchCommand blogSearchCommand) {
        workDistributor.insert(blogSearchCommand.getKeywordFromQuery());
        return requestService.search(blogSearchCommand.toSearchRequest());
    }
}
