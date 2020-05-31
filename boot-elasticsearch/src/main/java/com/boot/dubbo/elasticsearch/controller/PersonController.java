package com.boot.dubbo.elasticsearch.controller;

import com.boot.dubbo.elasticsearch.index.Person;
import com.boot.dubbo.elasticsearch.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

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

    private final AtomicLong atomicInteger = new AtomicLong(20);

    @GetMapping("/es/add")
    public Person add() {

        Person person = new Person();
        person.setId(atomicInteger.incrementAndGet());
        person.setAge(ThreadLocalRandom.current().nextInt(1, 100));
        person.setCity("city" + ThreadLocalRandom.current().nextInt(100));
        person.setProvince("province" + ThreadLocalRandom.current().nextInt(1, 34));
        person.setName("name" + ThreadLocalRandom.current().nextInt(1, 200));
        this.personService.add(person);
        return person;
    }

    @GetMapping("es/list/all")
    public List<Person> queryPerson() {

        return this.personService.queryPerson();
    }

    @GetMapping("es/delete/all")
    public String deleteAll() {

        this.personService.deleteAll();
        return "SUCCESS";
    }
}
