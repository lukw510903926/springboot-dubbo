package com.boot.dubbo.gson;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApolloValue {

    String key() default "";
}
