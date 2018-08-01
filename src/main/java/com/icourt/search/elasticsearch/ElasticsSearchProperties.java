package com.icourt.search.elasticsearch;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * elasticsearch配置
 *
 * @author: Caomr
 */
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
@Data
public class ElasticsSearchProperties {

    /**
     * 名称
     */
    private String clusterName;

    /**
     * 节点
     */
    private String clusterNodes;

}
