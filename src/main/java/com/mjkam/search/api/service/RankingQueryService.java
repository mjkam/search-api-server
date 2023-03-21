package com.mjkam.search.api.service;

import com.mjkam.search.api.domain.KeywordCount;
import com.mjkam.search.api.repository.KeywordCountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingQueryService {
    private final KeywordCountJpaRepository keywordCountJpaRepository;

    public List<KeywordCount> getTop10() {
        return keywordCountJpaRepository.findTop10OrderByCountDesc(PageRequest.of(0, 10));
    }
}
