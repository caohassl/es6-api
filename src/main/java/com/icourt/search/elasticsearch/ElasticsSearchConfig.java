package com.icourt.search.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * <p>ES客户端</p>
 *
 * @author: Caomr
 */
@Configuration
@EnableConfigurationProperties(ElasticsSearchProperties.class)
public class ElasticsSearchConfig {


    @Autowired
    private ElasticsSearchProperties elasticsSearchProperties;

    /**
     * 交由spring管理RestHighLevelClient
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return getElasticSearchHanlder().getRestHighLevelClient();
    }

    @Bean
    @Scope("singleton")
    public ElasticSearchHanlder getElasticSearchHanlder() {
        return new ElasticSearchHanlder(elasticsSearchProperties.getClusterNodes());
    }


}
