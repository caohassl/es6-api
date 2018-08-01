package com.icourt.search.elasticsearch;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Caomr on 2018/8/1.
 */
@Slf4j
public class ElasticsearchRepository<T> {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    public T findOne(String id) {
        try {
            Type type = getRepositoryType();
            String indexName = getIndexName(type);
            GetRequest getRequest = Requests.getRequest(indexName);
            getRequest.id(id);
            GetResponse getResponse = restHighLevelClient.get(getRequest);
            String resp = getResponse.getSourceAsString();
            T t = JSON.parseObject(resp, type);
            return t;
        } catch (Exception e) {
            log.error("method findone of es error,error msg stack as follow ", e);
        }
        return null;
    }

    private Type getRepositoryType() {
        Class clazz = this.getClass();
        ParameterizedType paraType = (ParameterizedType) clazz.getGenericSuperclass();
        Type type = paraType.getActualTypeArguments()[0];
        return type;
    }

    private String getIndexName(Type type){
        Document document = (Document) ((Class) type).getAnnotation(Document.class);
        String indexName ="";
        if(null!=document){
            indexName = document.indexName();
        }
        return indexName;
    }
}
