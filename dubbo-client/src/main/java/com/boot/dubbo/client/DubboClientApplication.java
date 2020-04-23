package com.boot.dubbo.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author : lukewei
 * @project : dubbo-client
 * @createTime : 2018年7月3日 : 上午10:15:50
 * @description :
 */
@Slf4j
@SpringBootApplication
public class DubboClientApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DubboClientApplication.class, args);
        log.info("dubbo client application start successfully---------");
    }

    /**
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
