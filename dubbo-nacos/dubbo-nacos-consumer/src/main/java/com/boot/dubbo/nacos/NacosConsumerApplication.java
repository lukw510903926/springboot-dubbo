package com.boot.dubbo.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/29 17:44
 **/
@SpringBootApplication
public class NacosConsumerApplication {

    private static Logger logger = LoggerFactory.getLogger(NacosConsumerApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(NacosConsumerApplication.class,args);
        logger.info("nacos consumer application start successfully=======");
    }
}
