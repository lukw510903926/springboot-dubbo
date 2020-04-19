package com.boot.dubbo.nacos;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SpringBootApplication
public class NacosConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(NacosConsumerApplication.class, args);
        log.info("nacos consumer application start successfully=======");
    }
}
