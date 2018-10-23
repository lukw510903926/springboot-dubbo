package com.dubbo.common.util.resdis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yagnqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月11日 下午1:30:43
 * @version V1.0
 */
public class RedisCacheImpl implements CacheService {

	private RedisTemplate<String, Object> redisTemplate;

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#setRedisTemplate(org.springframework.data.redis.core.RedisTemplate)
	 */
	@Override
	@Autowired
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	// =============================common============================
	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#expire(java.lang.String, long)
	 */
	@Override
	public boolean expire(String key, long time) {
		return redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#getExpire(java.lang.String)
	 */
	@Override
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hasKey(java.lang.String)
	 */
	@Override
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#del(java.lang.String)
	 */
	@Override
	public void del(String... key) {

		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(Arrays.asList(key));
			}
		}
	}

	// ============================String=============================
	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);

	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#set(java.lang.String, java.lang.Object, long)
	 */
	@Override
	public void set(String key, Object value, long time) {
		if (time > 0) {
			redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
		} else {
			set(key, value);
		}
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#increment(java.lang.String, long)
	 */
	@Override
	public long increment(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#decrement(java.lang.String, long)
	 */
	@Override
	public long decrement(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// ================================Map=================================
	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hget(java.lang.String, java.lang.String)
	 */
	@Override
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hmget(java.lang.String)
	 */
	@Override
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hmset(java.lang.String, java.util.Map)
	 */
	@Override
	public void hmset(String key, Map<String, Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hmset(java.lang.String, java.util.Map, long)
	 */
	@Override
	public void hmset(String key, Map<String, Object> map, long time) {
		redisTemplate.opsForHash().putAll(key, map);
		if (time > 0) {
			expire(key, time);
		}
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hset(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void hset(String key, String item, Object value) {
		redisTemplate.opsForHash().put(key, item, value);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hset(java.lang.String, java.lang.String, java.lang.Object, long)
	 */
	@Override
	public void hset(String key, String item, Object value, long time) {
		redisTemplate.opsForHash().put(key, item, value);
		if (time > 0) {
			expire(key, time);
		}
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hdel(java.lang.String, java.lang.Object)
	 */
	@Override
	public void hdel(String key, Object... items) {
		redisTemplate.opsForHash().delete(key, items);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hHasKey(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hincr(java.lang.String, java.lang.String, double)
	 */
	@Override
	public double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#hdecr(java.lang.String, java.lang.String, double)
	 */
	@Override
	public double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	// ============================set=============================
	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#sGet(java.lang.String)
	 */
	@Override
	public Set<Object> sGet(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#sHasKey(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean sHasKey(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#sSet(java.lang.String, java.lang.Object)
	 */
	@Override
	public long sSet(String key, Object... values) {
		return redisTemplate.opsForSet().add(key, values);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#sSetAndTime(java.lang.String, long, java.lang.Object)
	 */
	@Override
	public long sSetAndTime(String key, long time, Object... values) {
		Long count = redisTemplate.opsForSet().add(key, values);
		if (time > 0) {
			expire(key, time);
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#sGetSetSize(java.lang.String)
	 */
	@Override
	public long sGetSetSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#setRemove(java.lang.String, java.lang.Object)
	 */
	@Override
	public long setRemove(String key, Object... values) {
		return redisTemplate.opsForSet().remove(key, values);
	}
	// ===============================list=================================

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lGet(java.lang.String, long, long)
	 */
	@Override
	public List<Object> lGet(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lGetListSize(java.lang.String)
	 */
	@Override
	public long lGetListSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lGetIndex(java.lang.String, long)
	 */
	@Override
	public Object lGetIndex(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lSet(java.lang.String, java.lang.Object)
	 */
	@Override
	public void lSet(String key, Object value) {
		redisTemplate.opsForList().rightPush(key, value);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lSet(java.lang.String, java.lang.Object, long)
	 */
	@Override
	public void lSet(String key, Object value, long time) {
		redisTemplate.opsForList().rightPush(key, value);
		if (time > 0) {
			expire(key, time);
		}
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lSet(java.lang.String, java.util.List)
	 */
	@Override
	public Long lSet(String key, List<Object> value) {

		return redisTemplate.opsForList().rightPushAll(key, value);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lSet(java.lang.String, java.util.List, long)
	 */
	@Override
	public void lSet(String key, List<Object> value, long time) {
		redisTemplate.opsForList().rightPushAll(key, value);
		if (time > 0) {
			expire(key, time);
		}
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lUpdateIndex(java.lang.String, long, java.lang.Object)
	 */
	@Override
	public void lUpdateIndex(String key, long index, Object value) {
		redisTemplate.opsForList().set(key, index, value);
	}

	/* (non-Javadoc)
	 * @see com.tykj.common.util.redis.cacheService#lRemove(java.lang.String, long, java.lang.Object)
	 */
	@Override
	public long lRemove(String key, long count, Object value) {
		return redisTemplate.opsForList().remove(key, count, value);
	}

}