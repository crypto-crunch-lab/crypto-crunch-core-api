package com.crpyto.crunch.core;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ActiveProfiles("local")
@Slf4j
@SpringBootTest
class CrpytoCrunchCoreApiApplicationTests {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * ElasticSearch Index Request 테스트 코드
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-index.html
     *
     * @throws IOException
     */
    @Test
    void esIndexTest() throws IOException {
        IndexRequest request = new IndexRequest("posts").id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("result:  {}", response.toString());
    }

    /**
     * ElasticSearch Search Request 테스트 코드
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search.html
     *
     * @throws IOException
     */
    @Test
    void esSearchTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<String> results = Stream.of(response.getHits().getHits())
                .map(SearchHit::getSourceAsString)
                .collect(Collectors.toList());

        for (String r : results) {
            log.info("result: {}", r);
        }
    }


    /**
     * Redis opsForValue 테스트 코드
     * https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html#opsForValue--
     */
    @Test
    void redisTest() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";
        valueOperations.set(key, "hello");

        String value = valueOperations.get(key);
        log.info("result: {}", value);
    }
}
