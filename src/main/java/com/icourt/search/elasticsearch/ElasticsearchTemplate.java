package com.icourt.search.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Caomr
 * @date 2018/8/1
 */
@Component
public class ElasticsearchTemplate {

    @Autowired
    private RestHighLevelClient client;

    public List<JSONObject> search(SearchRequest request) {
        try {
            SearchResponse response = client.search(request);
            if (response.getHits() == null) {
                return null;
            }
            List<JSONObject> list = Lists.newArrayList();
            response.getHits().forEach(item -> list.add(JSON.parseObject(item.getSourceAsString())));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> search(SearchRequest request, Class<T> tClass) {
        List<JSONObject> searchResponse = this.search(request);
        if (searchResponse == null) {
            return null;
        }
        List<T> list = Lists.newArrayList();
        searchResponse.forEach(item -> list.add(JSON.parseObject(JSON.toJSONString(item), tClass)));
        return list;
    }
}
