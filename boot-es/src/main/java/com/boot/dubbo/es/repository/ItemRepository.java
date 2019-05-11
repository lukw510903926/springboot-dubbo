package com.boot.dubbo.es.repository;

import com.boot.dubbo.es.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-05-10 22:32
 * @email : lukewei@mockuai.com
 * @description :
 */
@Repository
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
}
