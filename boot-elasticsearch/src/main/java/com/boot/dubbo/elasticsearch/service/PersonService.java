package com.boot.dubbo.elasticsearch.service;

import com.boot.dubbo.elasticsearch.index.Person;
import com.boot.dubbo.elasticsearch.repository.PersonRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-05-30 12:28
 */
@Component
public class PersonService {

    @Resource
    private PersonRepository personRepository;

    public void add(Person person) {

        this.personRepository.save(person);
    }

    public List<Person> queryPerson() {

        Iterable<Person> all = this.personRepository.findAll();
        List<Person> list = new ArrayList<>();
        all.forEach(list::add);
        return list;
    }
}
