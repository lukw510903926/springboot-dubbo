package com.boot.dubbo.mvc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


/**
 * @author : lukewei
 * @project : dubbo-client
 * @createTime : 2018年7月3日 : 上午10:15:50
 * @description :
 * ServletComponentScan 扫描注解@WebFilter、@WebListener @WebServlet 注册filter listener servlet
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan("com.boot.dubbo.mvc")
@MapperScan(basePackages = "com.boot.dubbo.api.mapper")
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class DubboMvcApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DubboMvcApplication.class, args);
        log.info("dubbo mvc application start successfully---------");
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
