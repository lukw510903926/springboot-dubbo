package com.dubbo.common.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface NotNull {

    /**
     * 属性中文名称
     *
     * @return
     * @NotNull("姓名") String name
     */
    String value() default "";
}
