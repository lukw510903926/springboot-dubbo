package com.dubbo.common.util.resdis;

import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yagnqi@ywwl.com
 * @since 2018/10/12 17:56
 **/
public class RedisLock {

    private static final String LOCK_TITLE = "redisLock_";

    /**
     * 添加锁
     *
     * @param redisson
     * @param lockName
     * @param timeOut
     * @return
     */
    public static boolean getLock(Redisson redisson, String lockName, int timeOut) {

        try {
            String key = LOCK_TITLE + lockName;
            RLock rLock = redisson.getLock(key);
            //lock提供带timeout参数，timeout结束强制解锁，防止死锁
            rLock.lock(timeOut, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 释放锁
     *
     * @param redisson
     * @param lockName
     */
    public static void deleteLock(Redisson redisson, String lockName) {

        String key = LOCK_TITLE + lockName;
        RLock rLock = redisson.getLock(key);
        rLock.unlock();
    }
}