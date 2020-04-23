package com.boot.dubbo.gson;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-11-15 13:39
 * @email : lukewei@mockuai.com
 * @description :
 */
@Configuration
public class ApplicationConfig {

    @Bean
    @ConditionalOnProperty(prefix = "ali.ons.verify.withdrow", name = "msg", havingValue = "true")
    public Person person() {

        Person product = new Person();
        product.setUserName("ConditionalOnMissingBean");
        return product;
    }
}
