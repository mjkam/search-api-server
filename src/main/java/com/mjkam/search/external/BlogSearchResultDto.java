package com.mjkam.search.external;

import com.mjkam.search.external.provider.BlogDto;
import com.mjkam.search.external.provider.ClientResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BlogSearchResultDto {
    private int totalCount;
    private int pageableCount;
    private List<BlogDto> documents;

    private BlogSearchResultDto(int totalCount, int pageableCount, List<BlogDto> blogDtos) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.documents = blogDtos;
    }

    public static BlogSearchResultDto of(ClientResponse clientResponse) {
        return new BlogSearchResultDto(
                clientResponse.getTotalCount(),
                clientResponse.getPageableCount(),
                clientResponse.getDocuments()
        );
    }
}
