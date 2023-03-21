package com.mjkam.search.api.controller;

import com.mjkam.search.api.controller.dto.GetTop10KeywordResponse;
import com.mjkam.search.api.service.RankingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RankingApiController {
    private final RankingQueryService rankingQueryService;

    @GetMapping("/keyword/top10")
    public GetTop10KeywordResponse keywordTop10() {
        return new GetTop10KeywordResponse(rankingQueryService.getTop10());
    }
}
