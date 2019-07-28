package com.boot.dubbo.mvc.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author : yangqi
 * @email : lkw510903926@163.com
 * @description : redis配置
 * @since : 2019/7/28 11:25
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //配置要订阅的订阅项
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisConstants.PROVIDER_SUBSCRIBE_CHANNEL));
        return container;
    }
}
