package com.boot.dubbo.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : lukewei
 * @project : dubbo-server
 * @createTime : 2018年7月3日 : 上午9:33:27
 * @description :
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.boot.dubbo.api.mapper")
public class RestDubboApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestDubboApplication.class, args);
        log.info("dubbo server application start successfully -------");
    }

}
