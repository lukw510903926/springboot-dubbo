package com.boot.dubbo.nacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/29 17:28
 **/
@Slf4j
@SpringBootApplication
@NacosPropertySource(dataId = "dubbo-nacos-provider", autoRefreshed = true)
@DubboComponentScan(basePackages = "com.boot.dubbo.nacos.service")
public class NacosProviderApplication {

    public static void main(String[] args) {

        SpringApplication.run(NacosProviderApplication.class, args);
        log.info("dubbo nacos server application start successfully -------");
    }

}
