package com.boot.dubbo.mvc.controller;

import com.boot.dubbo.api.entity.User;
import com.boot.dubbo.mvc.redis.RedisConstants;
import com.boot.dubbo.mvc.redis.RedisProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * @author : yangqi
 * @email : lkw510903926@163.com
 * @description : redis
 * @since : 2019/7/28 10:46
 */
public class RedisController {

    @Autowired
    private RedisProvider redisProvider;

    @GetMapping("/redis/provider/send")
    public String send() {

        User user = new User();
        user.setName("provider");
        user.setUserName("redisProvider");
        user.setTestDate(new Date());
        this.redisProvider.sendMessage(RedisConstants.PROVIDER_SUBSCRIBE_CHANNEL, user);
        return "success";
    }
}
