package com.mjkam.search.external.provider.naver;

import com.mjkam.search.external.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


public class NaverTest {
//    @Autowired
//    private NaverBlogSearchApiRequester requester;
//
//    @Test
//    void test() {
//        BlogSearchRequest request = new BlogSearchRequest("ABC", 1, 10, SortingType.ACCURACY);
//        BlogSearchApiResponse response = requester.execute(request);
//        System.out.println(response.getTotalCount());
//        System.out.println(response.getPageableCount());
//        List<BlogInfo> documents = response.getDocuments();
//        for (BlogInfo blogInfo: documents) {
//            System.out.println(blogInfo);
//        }
//    }
//
    @Test
    void go() throws MalformedURLException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "vyBjLBntamQEtpAhJjBR");
        headers.set("X-Naver-Client-Secret", "4ruRd1CZqT");
        HttpEntity<Object> header = new HttpEntity<>(headers);


        String keyword = "편의점"; //이부부은 검색어를 UTF-8로 넣어줄거임.
        System.out.println("변환전:"+keyword);
        String query = null;
        try {
            query = URLEncoder.encode(keyword,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        System.out.println("변환후 :"+query);
//        try {
//            URL url = new URL("https://openapi.naver.com/v1/search/blog.json?query=" + query + "&start=1&display=1");
//            System.out.println(url);
//            ResponseEntity<NaverApiResponse> response =
//                    restTemplate.exchange(url.toURI(), HttpMethod.GET, header, NaverApiResponse.class);
//
//            NaverApiResponse responseBody = response.getBody();
//            if (responseBody == null) {
//                throw new BlogSearchApiException("Body should be not null");
//            }
//            for (var a : responseBody.getItems()) {
//                System.out.println(a.getBlogName());
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        URL url = new URL("https://openapi.naver.com/v1/search/blog.json?query=" + query + "&start=1&display=1");
//        String uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/v1/search/blog.json")
//                .queryParam("query", query)
//                .queryParam("start", 1)
//                .queryParam("display", 1)
//                .queryParam("sort", "sim")
//                .toUriString();

//        System.out.println(uri);

        ResponseEntity<NaverApiResponse> response =
                restTemplate.exchange(url.toURI(), HttpMethod.GET, header, NaverApiResponse.class);

        NaverApiResponse responseBody = response.getBody();
        if (responseBody == null) {
            throw new BlogSearchApiException("Body should be not null");
        }
        for (var a : responseBody.getItems()) {
            System.out.println(a.getBlogName());
        }

//        try {
//            ResponseEntity<NaverApiResponse> response =
//                    restTemplate.exchange(uri, HttpMethod.GET, header, NaverApiResponse.class);
//
//            NaverApiResponse responseBody = response.getBody();
//            if (responseBody == null) {
//                throw new BlogSearchApiException("Body should be not null");
//            }
//            for (var a : responseBody.getItems()) {
//                System.out.println(a.getBlogName());
//            }
//
//        } catch (HttpStatusCodeException e) {
//            System.out.println("==================");
//            System.out.println(e.getResponseBodyAsString());
////            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
////                    .body(e.getResponseBodyAsString());
//        }


    }
}
