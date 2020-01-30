package com.boot.dubbo.gson;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GsonController {

    @GetMapping("/gson/format")
    public Person gson(String name) {
        Person person = new Person();
        person.setBirthday(new Date());
        person.setId(1000L);
        person.setUserName("userName");
        return person;
    }

    @GetMapping("/gson/format/list")
    public List<Person> listUser(String name, Integer age, Date date) {

        List<Person> list = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            Person person = new Person();
            person.setBirthday(new Date());
            person.setId(ThreadLocalRandom.current().nextLong(1000, 2000));
            person.setUserName("userName :" + person.getId());
            list.add(person);
        }
        return list;
    }
}
