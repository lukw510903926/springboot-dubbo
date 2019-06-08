package com.boot.dubbo.nacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
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
 * @since 2018/12/29 17:28
 **/
@SpringBootApplication
@NacosPropertySource(dataId = "dubbo-nacos-provider", autoRefreshed = true)
@DubboComponentScan(basePackages = "com.boot.dubbo.nacos.service")
public class NacosProviderApplication {

    private static Logger logger = LoggerFactory.getLogger(NacosProviderApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(NacosProviderApplication.class, args);
        logger.info("dubbo nacos server application start successfully -------");
    }

}
