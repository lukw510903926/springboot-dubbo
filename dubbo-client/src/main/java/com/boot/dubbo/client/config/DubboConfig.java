package com.boot.dubbo.client.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.boot.dubbo.api.api.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/24 16:58
 **/
@Configuration
public class DubboConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ConsumerConfig consumerConfig;

    @Bean
    public IProductService referenceConfig() {

        ReferenceConfig<IProductService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(IProductService.class);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setConsumer(consumerConfig);
        return referenceConfig.get();
    }
}
