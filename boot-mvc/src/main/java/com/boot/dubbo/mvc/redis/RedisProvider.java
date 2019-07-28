package com.boot.dubbo.mvc.redis;

import com.boot.dubbo.api.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author : yangqi
 * @project : boot-dubbo
 * @email : lukewei@mockuai.com
 * @description : redis 消息发布者
 * @since : 2019/7/28 10:27
 */
@Slf4j
@Component
public class RedisProvider {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(String channel, User user) {

        log.info("=====================redis 消息发布=================");
        redisTemplate.opsForValue().set(RedisConstants.PROVIDER_SUBSCRIBE_CHANNEL,user);
        redisTemplate.convertAndSend(channel, user);
    }
}
