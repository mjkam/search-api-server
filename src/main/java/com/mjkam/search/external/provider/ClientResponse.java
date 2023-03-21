package com.mjkam.search.external.provider;

import java.util.List;

public interface ClientResponse {
    int getTotalCount();
    int getPageableCount();
    List<BlogDto> getDocuments();
}
