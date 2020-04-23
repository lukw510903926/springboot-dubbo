package com.boot.dubbo.gson;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GsonController {

    @GetMapping("/gson/format")
    public Person gson(@RequestParam Map<String, String> params) {
        log.info("name : {}", params);
        Person person = new Person();
        person.setBirthday(new Date());
        person.setId(1000L);
        person.setUserName("userName");
        return person;
    }

    @GetMapping("/gson/format/list")
    public List<Person> listUser(@RequestParam Map<String, String> params) {
        log.info("name : {}", params);
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
