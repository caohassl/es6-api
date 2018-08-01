package com.icourt.search.elasticsearch;

import com.google.common.collect.Lists;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 产生ES客户端中间类
 *
 * @author: Caomr
 */
public class ElasticSearchHanlder {

    private RestHighLevelClient instance;

    public ElasticSearchHanlder(String clusterNodes) {
        this.instance = getRestHighLevelClient(clusterNodes);
    }

    /**
     * 得到RestHighLevelClient实例
     *
     * @param
     * @return
     */
    public RestHighLevelClient getRestHighLevelClient() {
        return instance;
    }

    /**
     * 由集群ip字符串产生RestHighLevelClient
     *
     * @param clusterNodes
     * @return
     */
    private RestHighLevelClient getRestHighLevelClient(String clusterNodes) {
        RestHighLevelClient client = null;
        if (!StringUtils.isEmpty(clusterNodes)) {
            List<HttpHost> httpHosts = getHttpHostsByClusterNodes(clusterNodes);
            if (!CollectionUtils.isEmpty(httpHosts)) {
                client = new RestHighLevelClient(RestClient.builder((HttpHost[]) httpHosts.toArray()));
                return client;
            }
        }
        return client;
    }

    /**
     * 由集群ip字符串产生HttpHost的集合
     *
     * @param clusterNodes 集群ip字符串
     * @return
     */
    private List<HttpHost> getHttpHostsByClusterNodes(String clusterNodes) {
        if (!StringUtils.isEmpty(clusterNodes)) {
            List<String> nodesList = Arrays.asList(clusterNodes.split(","));
            List<HttpHost> httpHostsList = Lists.newArrayList();
            nodesList.forEach(nodeStr -> httpHostsList.add(HttpHost.create(nodeStr)));
            return httpHostsList;
        }
        return null;
    }

}
