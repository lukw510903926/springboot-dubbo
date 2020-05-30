package com.boot.dubbo.elasticsearch.repository;

import com.boot.dubbo.elasticsearch.index.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-05-30 12:27
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
