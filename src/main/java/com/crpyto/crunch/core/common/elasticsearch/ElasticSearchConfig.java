package com.crpyto.crunch.core.common.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
    @Bean
    public RestClientBuilder esRestClient(
            @Value("${elasticsearch.host}") String host,
            @Value("${elasticsearch.port}") Integer port,
            @Value("${elasticsearch.scheme}") String scheme,
            @Value("${elasticsearch.username}") String username,
            @Value("${elasticsearch.password}") String password) {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        return RestClient.builder(new HttpHost(host, port, scheme))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder esRestClient) {
        return new RestHighLevelClient(esRestClient);
    }
}
