package com.boot.dubbo.mvc;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */

@SpringBootApplication
@ServletComponentScan("com.boot.dubbo.mvc")//扫描注解@WebFilter、@WebListener @WebServlet 注册filter listener servlet
@MapperScan(basePackages = "com.boot.dubbo.api.mapper")
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class DubboMVCApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(DubboMVCApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DubboMVCApplication.class, args);
        logger.info("dubbo mvc application start successfully---------");
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
