package com.dubbo.common.util.cache;

import com.dubbo.common.util.aop.CacheDelete;
import com.dubbo.common.util.aop.CachePut;
import com.dubbo.common.util.aop.Cacheable;
import com.dubbo.common.util.aop.SpringExpressionUtils;
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
public class RedisCacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    private static final Class<Object> LOCK = Object.class;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(com.dubbo.common.util.aop.Cacheable)")
    public Object cacheGet(final ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = getMethod(joinPoint);
        Cacheable cache = method.getAnnotation(Cacheable.class);
        String key = this.getKey(cache.key(), cache.prefix(), joinPoint);
        if (StringUtils.isBlank(key)) {
            return joinPoint.proceed();
        }
        String cacheName = cache.cacheNames();
        Object value = this.getCache(cacheName, key);
        if (value == null) {
            synchronized (LOCK) {
                value = this.getCache(cacheName, key);
                if (value == null) {
                    value = joinPoint.proceed();
                }
            }
        }
        this.setCache(cacheName, key, value, cache.expire());
        logger.info("cacheName值：{},key值：{}", cacheName, key);
        return value;
    }

    @Around("@annotation(com.dubbo.common.util.aop.CachePut)")
    public Object cachePut(final ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = getMethod(joinPoint);
        CachePut cache = method.getAnnotation(CachePut.class);
        String key = this.getKey(cache.key(), cache.prefix(), joinPoint);
        this.deleteCache(cache.cacheNames(), key);
        Object value = joinPoint.proceed();
        this.setCache(cache.cacheNames(), key, value, cache.expire());
        return value;
    }

    @Around("@annotation(com.dubbo.common.util.aop.CacheDelete)")
    public Object cacheDelete(final ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = getMethod(joinPoint);
        CacheDelete cache = method.getAnnotation(CacheDelete.class);
        String keyValue = this.getKey(cache.key(), cache.prefix(), joinPoint);
        this.deleteCache(cache.cacheName(),keyValue);
        Object value = joinPoint.proceed();
        if (StringUtils.isBlank(keyValue)) {
            return value;
        }
        this.deleteCache(cache.cacheName(), keyValue);
        logger.info("cacheDelete执行,name值：{},key值：{}", cache.cacheName(), keyValue);
        return value;
    }

    /**
     * 缓存删除
     *
     * @param cacheName
     * @param key
     */
    private void deleteCache(String cacheName, String key) {

        if (StringUtils.isNotBlank(cacheName)) {
            redisTemplate.opsForHash().delete(cacheName, key);
        } else {
            redisTemplate.delete(key);
        }
    }

    /**
     * 设置缓存
     *
     * @param cacheName
     * @param key
     * @param value
     * @param expire
     */
    private void setCache(String cacheName, String key, Object value, long expire) {

        if (value == null) {
            return;
        }
        if (StringUtils.isNotBlank(cacheName)) {
            redisTemplate.opsForHash().put(cacheName, key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    private Object getCache(String cacheName, String key) {

        if (StringUtils.isNotBlank(cacheName)) {
            return redisTemplate.opsForHash().get(cacheName, key);
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取key
     *
     * @param key
     * @param prefix
     * @param joinPoint
     * @return
     */
    private String getKey(String key, String prefix, ProceedingJoinPoint joinPoint) {

        Method method = this.getMethod(joinPoint);
        String tempKey = SpringExpressionUtils.parseValue(key, method, joinPoint.getArgs(), String.class);
        if (StringUtils.isBlank(tempKey)) {
            return null;
        }
        return prefix + ':' + tempKey;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }
}
