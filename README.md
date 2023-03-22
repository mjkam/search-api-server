# 블로그 open api 프로젝트

Kakao, Naver Api 를 이용한 blog 검색 서비스

## 실행 Jar 파일 다운로드
[다운로드](https://github.com/mjkam/search-api-server/raw/master/search-0.0.1-SNAPSHOT.jar)

## 블로그 검색 API

### Request

```
GET /search/blog
```


#### parameter

| NAME  | TYPE    | description           | Required |
|-------|---------|-----------------------|----------|
| query | String  | 길이 1 ~ 255            | O        |
| page  | Integer | 1 ~ 50 사이의 값. 기본 값 1  | X        |
| size  | Integer | 1 ~ 50 사이의 값. 기본 값 10 | X        |
| sort  | String  | accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy              | X        |

### Response

| NAME          | TYPE           | description             |
|---------------|----------------|-------------------------|
| totalCount    | String         | 검색된 문서 수                |
| pageableCount | Integer        | total_count 중 노출 가능 문서 수 |
| documents     | List(BlogInfo) | 블로그 정보                  |

#### BlogInfo

| NAME      | TYPE          | description                      |
|-----------|---------------|----------------------------------|
| title     | String        | 블로그 글 제목                         |
| blogName  | String        | 블로그의 이름                          |
| contents  | String        | 블로그 글 요약                         |
| url       | String        | 블로그 글 URL                        |
| thumbnail | String        | 검색 시스템에서 추출한 대표 미리보기 이미지 URL     |
| postDate  | LocalDateTime | 블로그 글 작성시간(yyyy-MM-dd`T`HH:ii:ss |

## 인기검색어 Top10 API

### Request

```
GET /keyword/top10
```
### Request



### Response

| NAME          | TYPE               | description      |
|---------------|--------------------|------------------|
| keywords      | List(KeywordCount) | 검색 수 기준 내림차순 리스트 |


### KeywordCount

| NAME      | TYPE           | description |
|-----------|----------------|------------|
| keyword   | String         | 검색된 키워드    |
| count     | Long           | 검색된 수      |



## Dependencies

* resilience4j-spring-boot2(starter-aop, starter-actuator) : Circuit Breaker 설정 (메인 API 실패 시 fallback API 실행)
* apache-httpcomponents : RestTemplate 설정
* lombok : 어노테이션 기반 코드 자동완성