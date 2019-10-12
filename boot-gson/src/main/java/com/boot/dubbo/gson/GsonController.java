package com.boot.dubbo.gson;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    @GetMapping("/gson/format")
    public Person listUser(String name, Integer age, Date date) {
        Person person = new Person();
        person.setBirthday(new Date());
        person.setId(1000L);
        person.setUserName("userName");
        return person;
    }
}
