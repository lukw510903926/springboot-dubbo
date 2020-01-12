package com.boot.dubbo.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : lukewei
 * @project : dubbo-server
 * @createTime : 2018年7月3日 : 上午9:33:27
 * @description :
 */
@SpringBootApplication
@MapperScan(basePackages = "com.boot.dubbo.api.mapper")
@EnableDubbo(scanBasePackages = "com.boot.dubbo.server.service")
public class DubboServerApplication {

    private static Logger logger = LoggerFactory.getLogger(DubboServerApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(DubboServerApplication.class, args);
        logger.info("dubbo server application start successfully -------");
    }

}
