package com.boot.dubbo.mvc.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author : yangqi
 * @email : lkw510903926@163.com
 * @description : redis 消息订阅
 * @since : 2019/7/28 10:39
 */
@Slf4j
public class RedisSubscribe extends MessageListenerAdapter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("=================redis 消息订阅===================");
        byte[] body = message.getBody();
        Object msgBody = redisTemplate.getValueSerializer().deserialize(body);
        System.out.println(msgBody);
        byte[] channel = message.getChannel();
        Object msgChannel = redisTemplate.getKeySerializer().deserialize(channel);
        System.out.println(msgChannel);
        String msgPattern = new String(pattern);
        System.out.println(msgPattern);
    }
}
