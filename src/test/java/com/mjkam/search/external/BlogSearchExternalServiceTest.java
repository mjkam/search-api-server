package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderClient;
import com.mjkam.search.external.provider.ProviderType;
import com.mjkam.search.external.provider.SortingType;
import com.mjkam.search.external.provider.kakao.support.KakaoApiResponseCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogSearchExternalServiceTest extends ExternalBaseTest{
    private BlogSearchExternalService sut;

    @Test
    @DisplayName("등록되지 않은 Provider 요청이 오면 예외 발생")
    void throwException_whenRequesterNotFound() {
        //given
        int totalCount = 100;
        ProviderType providerType = ProviderType.KAKAO;

        MockProviderClient mockRequester = new MockProviderClient(totalCount, providerType);
        sut = initBlogSearchRequestService(ProviderType.NAVER, mockRequester);

        BlogSearchRequest request = new BlogSearchRequest("DUMMY", 1, 1, SortingType.ACCURACY);

        //when
        Assertions.assertThatThrownBy(() -> sut.search(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Kakao Api 가 메인이고 Kakao Requester 가 등록되어 있을 때, BlogSearchService 정상 호출")
    void success_whenMainProviderTypeAndRequesterProviderTypeIsSame() {
        //given
        int totalCount = 100;
        ProviderType providerType = ProviderType.KAKAO;

        MockProviderClient mockRequester = new MockProviderClient(totalCount, providerType);
        sut = initBlogSearchRequestService(providerType, mockRequester);

        BlogSearchRequest request = new BlogSearchRequest("DUMMY", 1, 1, DUMMY_SORTING_TYPE);

        //when
        BlogSearchResultDto response = sut.search(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCount);
        assertThat(mockRequester.receivedRequest).isEqualTo(request);
    }

    private BlogSearchExternalService initBlogSearchRequestService(ProviderType mainProviderType, ProviderClient requester) {
        ProviderClientManager requesterManager = new ProviderClientManager(List.of(requester));
        ProviderCircuitConfiguration requesterConfiguration = new ProviderCircuitConfiguration();
        requesterConfiguration.setMain(mainProviderType);
        return new BlogSearchExternalService(requesterConfiguration, requesterManager);
    }

    private static class MockProviderClient implements ProviderClient {
        private final BlogSearchResultDto apiResponse;
        private final ProviderType providerType;

        private BlogSearchRequest receivedRequest;

        public MockProviderClient(int totalCount, ProviderType providerType) {
            this.apiResponse = KakaoApiResponseCreator.createApiResponse(totalCount, totalCount, 1, 1);
            this.providerType = providerType;
        }

        @Override
        public BlogSearchResultDto execute(BlogSearchRequest request) {
            this.receivedRequest = request;
            return apiResponse;
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return providerType.equals(this.providerType);
        }
    }
}
