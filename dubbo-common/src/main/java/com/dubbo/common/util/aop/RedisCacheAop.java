package com.dubbo.common.util.aop;

import com.dubbo.common.util.resdis.CacheDelete;
import com.dubbo.common.util.resdis.CachePut;
import com.dubbo.common.util.resdis.Cacheable;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
public class RedisCacheAop {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAop.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(com.dubbo.common.util.resdis.Cacheable)")
    public Object cacheGet(final ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = getMethod(joinPoint);
        Cacheable cache = method.getAnnotation(Cacheable.class);
        String key = SpringExpressionUtils.parseValue(cache.key(), method, joinPoint.getArgs(), String.class);
        if (StringUtils.isBlank(key)) {
            return joinPoint.proceed();
        }

        Object value;
        String cacheName = cache.cacheNames();
        if (StringUtils.isNotBlank(cacheName)) {
            value = redisTemplate.opsForHash().get(cacheName, key);
        } else {
            value = redisTemplate.opsForValue().get(key);
        }
        logger.info("cacheAble,name值：{},key值：{}", cacheName, key);
        if (value == null) {
            value = joinPoint.proceed();
        }
        this.setCache(cacheName, key, value, cache.expire());
        return value;
    }

    @Around("@annotation(com.dubbo.common.util.resdis.CachePut)")
    public Object cachePut(final ProceedingJoinPoint joinPoint) throws Throwable {

        Object value = joinPoint.proceed();
        Method method = getMethod(joinPoint);
        CachePut cache = method.getAnnotation(CachePut.class);
        String key = SpringExpressionUtils.parseValue(cache.key(), method, joinPoint.getArgs(), String.class);
        this.setCache(cache.cacheNames(), key, value, cache.expire());
        return value;
    }

    @Around("@annotation(com.dubbo.common.util.resdis.CacheDelete)")
    public Object cacheDelete(final ProceedingJoinPoint joinPoint) throws Throwable {

        Object value = joinPoint.proceed();
        Method method = getMethod(joinPoint);
        CacheDelete cache = method.getAnnotation(CacheDelete.class);
        String keyValue = SpringExpressionUtils.parseValue(cache.key(), method, joinPoint.getArgs(), String.class);
        if (StringUtils.isBlank(keyValue)) {
            return value;
        }
        String cacheName = cache.cacheName();
        if (StringUtils.isNotBlank(cacheName)) {
            redisTemplate.opsForHash().delete(cacheName, keyValue);
        } else {
            redisTemplate.delete(keyValue);
        }
        logger.info("cacheDelete执行,name值：{},key值：{}", cacheName, keyValue);
        return value;
    }

    private void setCache(String cacheName, String key, Object value, long expire) {

        if (value == null || StringUtils.isBlank(key)) {
            return;
        }
        if (StringUtils.isNotBlank(cacheName)) {
            redisTemplate.opsForHash().put(cacheName, key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MICROSECONDS);
        }
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }
}
