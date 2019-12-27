package com.boot.dubbo.gson.redis;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-12-27 15:03
 * @email : lukewei@mockuai.com
 * @description :
 */
@Slf4j
@RestController
public class RedisController {

    @Autowired
    private JedisPool jedisPool;

    String key = "wdzg:ad:hash123456";

    @GetMapping("/jedis/hash/incr")
    public Object hash() {

        Jedis jedis = this.jedisPool.getResource();
        jedis.hincrBy("wdzg:ad:hash123456", "amount", 100);
        jedis.hincrBy("wdzg:ad:hash123456", "totalAmount", 100);
        Map<String, String> map = jedis.hgetAll(this.key);
        jedis.close();
        return map;
    }

    @GetMapping("/jedis/zSet/incr")
    public Object zSet() {

        Jedis jedis = this.jedisPool.getResource();
        jedis.zadd("wdzg:ad:zset", 1000, "hash123456");
        jedis.zadd("wdzg:ad:zset", 2000, "hash123456");
        Long index = jedis.zrank("wdzg:ad:zset", "hash1234567");
        if (index == null) {
            jedis.zadd("wdzg:ad:zset", 2000, "hash1234567");
        }
        jedis.zadd("wdzg:ad:zset", 3000, "hash1234567");
        Set<String> zrange = jedis.zrange("wdzg:ad:zset", 0, 0);
        jedis.zrem("wdzg:ad:zset", Lists.newArrayList(zrange).get(0));
        Long hash12345679 = jedis.zrank("wdzg:ad:zset", "hash12345679");
        jedis.close();
        return zrange;
    }
}
