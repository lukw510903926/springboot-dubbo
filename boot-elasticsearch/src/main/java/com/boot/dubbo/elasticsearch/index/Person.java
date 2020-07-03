package com.boot.dubbo.elasticsearch.index;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-05-17 22:37
 */
@Data
@Document(indexName = "t_person_index", type = "t_person_document")
public class Person implements Serializable {

    private static final long serialVersionUID = 7631727984669080957L;

    @Id
    private Long id;

    private String name;

    private String city;

    private String province;

    private Integer age;
}
