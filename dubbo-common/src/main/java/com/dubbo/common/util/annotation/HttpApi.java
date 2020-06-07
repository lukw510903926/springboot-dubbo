package com.dubbo.common.util.annotation;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-06-07 21:00
 */
public @interface HttpApi {

    /**
     * 请求路径
     *
     * @return
     */
    String url() default "";

    /**
     * 请求方法
     *
     * @return
     */
    String method() default "GET";

    /**
     * 是否支持上传
     *
     * @return
     */
    boolean upload() default false;
}
