package com.boot.dubbo.elasticsearch.controller;

import com.boot.dubbo.elasticsearch.index.Person;
import com.boot.dubbo.elasticsearch.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-05-30 12:33
 */
@RestController
public class PersonController {

    @Resource
    private PersonService personService;

    @GetMapping("/es/add")
    public Person add() {

        Person person = new Person();
        person.setAge(ThreadLocalRandom.current().nextInt());
        person.setCity("city");
        this.personService.add(person);
        return person;
    }

    @GetMapping("es/list")
    public List<Person> queryPerson() {

        return this.personService.queryPerson();
    }
}
