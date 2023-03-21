package com.mjkam.search.api.service;

import com.mjkam.search.api.repository.KeywordCountJpaRepository;
import com.mjkam.search.api.service.dto.BlogSearchCommand;
import com.mjkam.search.external.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogSearchService {
    private final KeywordCountJpaRepository keywordCountJpaRepository;
    private final BlogSearchRequestService requestService;

    @Transactional
    public BlogSearchApiResponse search(BlogSearchCommand blogSearchCommand) {
        keywordCountJpaRepository.updateKeywordCount(blogSearchCommand.getQuery());
        return requestService.search(blogSearchCommand.toSearchRequest());
    }
}
