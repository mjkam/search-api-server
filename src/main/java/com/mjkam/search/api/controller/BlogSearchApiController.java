package com.mjkam.search.api.controller;

import com.mjkam.search.api.controller.dto.BlogSearchResponse;
import com.mjkam.search.api.service.BlogSearchService;
import com.mjkam.search.api.service.dto.BlogSearchCommand;
import com.mjkam.search.external.BlogSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
@Validated
public class BlogSearchApiController {
    private final BlogSearchService blogSearchService;

    @GetMapping("/search/blog")
    public BlogSearchResponse search(
            @RequestParam @NotEmpty String query,
            @RequestParam(required = false, defaultValue = "1") @Min(1) @Max(50) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(50) Integer size,
            @RequestParam(required = false, defaultValue = "accuracy") String sort
    ) {
        BlogSearchResultDto response = blogSearchService.search(new BlogSearchCommand(query, page, size, sort));
        return new BlogSearchResponse(response);
    }
}
