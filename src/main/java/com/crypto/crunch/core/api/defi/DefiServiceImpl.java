package com.crypto.crunch.core.api.defi;

import com.crypto.crunch.core.domain.defi.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DefiServiceImpl implements DefiService {

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;

    private static final String DEFI_INDEX = "defi";
    private static final int DEFAULT_SEARCH_SIZE = 5000;
    private static final String DEFAULT_SORT_FIELD = "tvl";

    public DefiServiceImpl(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Defi> search(DefiRequest request) throws Exception {
        SearchRequest searchRequest = new SearchRequest(DEFI_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(ObjectUtils.isEmpty(request.getSize()) ? DEFAULT_SEARCH_SIZE : request.getSize());

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(request.getSearchKeyword())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", request.getSearchKeyword()));
        }

        if (ObjectUtils.isEmpty(request.getSorts())) {
            searchSourceBuilder.sort(DEFAULT_SORT_FIELD, SortOrder.DESC);
        } else {
            DefiRequestSorts sorts = request.getSorts();
            String field = sorts.getField();
            String order = sorts.getOrder();

            searchSourceBuilder.sort(field, StringUtils.equals(order, "DESC") ? SortOrder.DESC : SortOrder.ASC);
        }

        if (!ObjectUtils.isEmpty(request.getFilters())) {
            BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();

            DefiRequestFilters filters = request.getFilters();
            String network = filters.getNetwork();
            DefiConf.DefiTvlRangeType tvlRange = filters.getTvlRange();
            DefiConf.DefiApyRangeType apyRange = filters.getApyRange();

            if (StringUtils.isNotEmpty(network)) {
                subBoolQueryBuilder.must(QueryBuilders.termQuery("network.keyword", network));
            }
            if (!ObjectUtils.isEmpty(tvlRange)) {
                subBoolQueryBuilder.must(QueryBuilders.rangeQuery("tvl").gte(tvlRange.value()));
            }
            if (!ObjectUtils.isEmpty(apyRange)) {
                subBoolQueryBuilder.must(QueryBuilders.rangeQuery("apy").gte(apyRange.value()));
            }

            boolQueryBuilder.must(subBoolQueryBuilder);
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Stream.of(response.getHits().getHits())
                .map(SearchHit::getSourceAsString)
                .map(str -> {
                    try {
                        return objectMapper.readValue(str, Defi.class);
                    } catch (IOException e) {
                        log.error("error: {}", e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getNetworks() throws IOException {
        SearchRequest searchRequest = new SearchRequest(DEFI_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders
                .terms("terms")
                .field("network.keyword");
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregation aggregation = response.getAggregations().get("terms");

        if (ObjectUtils.isEmpty(aggregation)) {
            return Collections.emptyList();
        }

        return ((ParsedStringTerms) aggregation).getBuckets()
                .stream()
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .collect(Collectors.toList());
    }
}
