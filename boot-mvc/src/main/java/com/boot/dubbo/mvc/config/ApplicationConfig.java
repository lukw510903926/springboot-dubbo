package com.boot.dubbo.mvc.config;

import com.dubbo.common.util.resdis.CacheService;
import com.dubbo.common.util.resdis.RedisCacheImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * @Description
 * </p>
 * @author yangqi
 * @since 2018/10/12 15:17
 * @email yangqi@ywwl.com
 **/
@Configuration
public class ApplicationConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory ;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheService cacheService() {

        RedisCacheImpl redisCache = new RedisCacheImpl();
        redisCache.setRedisTemplate(redisTemplate());
        return redisCache;
    }
}
