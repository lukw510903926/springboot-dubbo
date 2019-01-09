package com.dubbo.common.util.resdis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheDelete {

	/**
	 * hash 缓存时的缓存名称
	 * 
	 * @return
	 */
	String cacheName();

	/**
	 * 缓存key
	 * 
	 * @return
	 */
	String key();

	/**
	 * 缓存key前缀
	 * @return
	 */
	String prefix() default "";
}
