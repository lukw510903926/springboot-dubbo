package com.dubbo.common.util.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CachePut {

	/**
	 * 缓存类型为hash时的 缓存名称
	 * @return
	 */
	String cacheNames() default "";

	/**
	 * key
	 * @return
	 */
	String key();

	/**
	 * 过期时间
	 * @return
	 */
	long expire() default 1800;

	/**
	 * 缓存key前缀
	 * @return
	 */
	String prefix();
}
