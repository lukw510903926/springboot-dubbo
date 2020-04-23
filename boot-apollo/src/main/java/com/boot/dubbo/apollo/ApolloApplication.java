package com.boot.dubbo.apollo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-12-26 10:37
 * @email : lukewei@mockuai.com
 * @description :
 */
@Slf4j
@SpringBootApplication
public class ApolloApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApolloApplication.class, args);
        log.info("apollo application start successfully");
    }
}
