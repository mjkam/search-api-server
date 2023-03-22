package com.mjkam.search.api.controller;

import com.mjkam.search.api.controller.dto.BlogSearchResponse;
import com.mjkam.search.api.service.BlogSearchService;
import com.mjkam.search.api.service.dto.BlogSearchCommand;
import com.mjkam.search.external.BlogSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogSearchApiController {
    private final BlogSearchService blogSearchService;

    @GetMapping("/search/blog")
    public BlogSearchResponse search(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "1") String size,
            @RequestParam(required = false, defaultValue = "accuracy") String sort
    ) {
        BlogSearchResultDto response = blogSearchService.search(new BlogSearchCommand(query, page, size, sort));
        return new BlogSearchResponse(response);
    }
}