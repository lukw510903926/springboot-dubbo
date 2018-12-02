package com.dubbo.common.util.resdis;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {

    /**
     * 缓存类型为hash时的 缓存名称
     * @return
     */
    String cacheNames() default "";

    /**
     * key
     * @return
     */
    String key() default "";

    /**
     * 过期时间
     * @return
     */
    long expire() default 1800;

    /**
     * 返回结果类型
     * @return
     */
    Class<?> resultType() default Object.class;
}
