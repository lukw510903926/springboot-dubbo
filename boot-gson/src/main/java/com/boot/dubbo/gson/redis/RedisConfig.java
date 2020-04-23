package com.boot.dubbo.gson.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-12-27 15:02
 * @email : lukewei@mockuai.com
 * @description :
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return new JedisPool(jedisPoolConfig, "daily.redis.mockuai.com", 6379, 2000);
    }
}
