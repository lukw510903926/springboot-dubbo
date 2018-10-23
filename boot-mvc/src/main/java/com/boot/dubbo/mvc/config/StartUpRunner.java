package com.boot.dubbo.mvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * @Description springboot 启动后执行run方法
 * </p>
 * @author yangqi
 * @since 2018/10/12 11:22
 * @email yagnqi@ywwl.com
 **/
@Component
public class StartUpRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(StartUpRunner.class);

    @Override
    public void run(String... args) {

        logger.info("springboot启动后执行此方法");
    }
}
