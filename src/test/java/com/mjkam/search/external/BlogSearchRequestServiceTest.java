package com.mjkam.search.external;

import com.mjkam.search.external.provider.ProviderType;
import com.mjkam.search.external.provider.kakao.KakaoApiResponse;
import com.mjkam.search.external.provider.kakao.support.KakaoApiResponseCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogSearchRequestServiceTest {
    private BlogSearchRequestService sut;

    @Test
    @DisplayName("Kakao Api 가 메인일 때, BlogSearchService 정상 호출")
    void throwException_whenRequesterNotFound() {
        //given
        int totalCount = 100;
        ProviderType providerType = ProviderType.KAKAO;
        MockKakaoBlogSearchApiRequester requester = new MockKakaoBlogSearchApiRequester(totalCount, providerType);
        sut = createBlogSearchService(ProviderType.NAVER, requester);

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
        MockKakaoBlogSearchApiRequester requester = new MockKakaoBlogSearchApiRequester(totalCount, providerType);
        sut = createBlogSearchService(providerType, requester);

        BlogSearchRequest request = new BlogSearchRequest("DUMMY", 1, 1, SortingType.ACCURACY);

        //when
        BlogSearchApiResponse response = sut.search(request);

        //then
        assertThat(response.getTotalCount()).isEqualTo(totalCount);
        assertThat(requester.receivedRequest).isEqualTo(request);
    }

    private BlogSearchRequestService createBlogSearchService(ProviderType mainProviderType, BlogSearchApiRequester requester) {
        BlogSearchApiRequesterManager requesterManager = new BlogSearchApiRequesterManager(List.of(requester));
        BlogSearchApiRequesterConfiguration requesterConfiguration = new BlogSearchApiRequesterConfiguration();
        requesterConfiguration.setMain(mainProviderType);
        return new BlogSearchRequestService(requesterConfiguration, requesterManager);
    }

    private static class MockKakaoBlogSearchApiRequester implements BlogSearchApiRequester {
        private final KakaoApiResponse kakaoApiResponse;
        private final ProviderType providerType;

        private BlogSearchRequest receivedRequest;

        public MockKakaoBlogSearchApiRequester(int totalCount, ProviderType providerType) {
            this.kakaoApiResponse = KakaoApiResponseCreator.create(totalCount, totalCount, 1, 1);
            this.providerType = providerType;
        }

        @Override
        public BlogSearchApiResponse execute(BlogSearchRequest request) {
            this.receivedRequest = request;
            return BlogSearchApiResponse.fromKakao(1, 1, this.kakaoApiResponse);
        }

        @Override
        public boolean isMatch(ProviderType providerType) {
            return providerType.equals(this.providerType);
        }
    }
}
