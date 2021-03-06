package com.crypto.crunch.core.api.defi.service;

import com.crypto.crunch.core.domain.defi.conf.DefiConf;
import com.crypto.crunch.core.domain.defi.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
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

    public DefiServiceImpl(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Defi> search(DefiRequest request) throws Exception {
        SearchRequest searchRequest = new SearchRequest(DefiConf.DEFI_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(ObjectUtils.isEmpty(request.getSize()) ? DefiConf.DEFI_INDEX_DEFAULT_SEARCH_SIZE : request.getSize());

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(request.getSearchKeyword())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", request.getSearchKeyword()));
        }

        if (ObjectUtils.isEmpty(request.getSorts())) {
            searchSourceBuilder.sort(DefiConf.DEFI_INDEX_DEFAULT_SORT_FIELD, SortOrder.DESC);
        } else {
            DefiRequestSorts sorts = request.getSorts();
            String field = sorts.getField();
            String order = sorts.getOrder();

            searchSourceBuilder.sort(field, StringUtils.equals(order, "DESC") ? SortOrder.DESC : SortOrder.ASC);
        }

        if (!ObjectUtils.isEmpty(request.getFilters())) {
            BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();

            DefiRequestFilters filters = request.getFilters();
            String networkName = filters.getNetwork();
            DefiConf.DefiTvlRangeType tvlRange = filters.getTvlRange();
            DefiConf.DefiApyRangeType apyRange = filters.getApyRange();

            if (StringUtils.isNotEmpty(networkName) && !StringUtils.equals(networkName, "ALL")) {
                subBoolQueryBuilder.must(QueryBuilders.termQuery("network.name.keyword", networkName));
            }
            if (!ObjectUtils.isEmpty(tvlRange)) {
                subBoolQueryBuilder.must(QueryBuilders.rangeQuery("tvl").gte(tvlRange.value()));
            }
            if (!ObjectUtils.isEmpty(apyRange)) {
                subBoolQueryBuilder.must(QueryBuilders.rangeQuery("apy").gte(apyRange.value()));
            }

            boolQueryBuilder.must(subBoolQueryBuilder);
        }

        if (request.getExposureType().equals(DefiConf.DefiExposureType.SVC)) {
            boolQueryBuilder.must(QueryBuilders.termQuery("isService", true));
        }
        if (request.getExposureType().equals(DefiConf.DefiExposureType.RECOMM)) {
            boolQueryBuilder.must(QueryBuilders.termQuery("isRecommend", true));
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.fetchSource(null, new String[]{"apySeries", "tvlSeries"});
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
    public Defi getDefiById(String id) throws IOException {
        GetRequest getRequest = new GetRequest(DefiConf.DEFI_INDEX, id);
        return objectMapper.readValue(restHighLevelClient.get(getRequest, RequestOptions.DEFAULT).getSourceAsString(), Defi.class);
    }

    @Override
    public List<String> getNetworks() throws IOException {
        SearchRequest searchRequest = new SearchRequest(DefiConf.DEFI_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders
                .terms("terms")
                .field("network.name.keyword");
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregation aggregation = response.getAggregations().get("terms");

        if (ObjectUtils.isEmpty(aggregation)) {
            return Collections.emptyList();
        }

        List<String> list = ((ParsedStringTerms) aggregation).getBuckets()
                .stream()
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .collect(Collectors.toList());

        list.add(0, "ALL");
        return list;
    }

    @Override
    public Boolean updateDefi(Defi defi) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(DefiConf.DEFI_INDEX, defi.getId());
        updateRequest.doc(objectMapper.writeValueAsString(defi), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().equals(RestStatus.OK);
    }

    @Override
    public List<DefiPlatform> getPlatforms() throws IOException {
        SearchRequest searchRequest = new SearchRequest(DefiConf.DEFI_PLATFORM_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("name.keyword");
        searchSourceBuilder.size(DefiConf.DEFI_PLATFORM_INDEX_DEFAULT_SEARCH_SIZE);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Stream.of(response.getHits().getHits())
                .map(SearchHit::getSourceAsString)
                .map(str -> {
                    try {
                        return objectMapper.readValue(str, DefiPlatform.class);
                    } catch (IOException e) {
                        log.error("error: {}", e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean updatePlatform(DefiPlatform platform) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(DefiConf.DEFI_PLATFORM_INDEX, platform.getId());
        updateRequest.doc(objectMapper.writeValueAsString(platform), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().equals(RestStatus.OK);
    }
}
