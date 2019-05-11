package com.boot.dubbo.es.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-05-10 22:30
 * @email : lukewei@mockuai.com
 * @description :
 */
@Document(indexName = "ad",type = "ad_item")
//indexName索引名称 可以理解为数据库名 必须为小写 不然会报org.elasticsearch.indices.InvalidIndexNameException异常 type类型 可以理解为表名
public class Item implements Serializable {

    private static final long serialVersionUID = -3836912878705531343L;
}
