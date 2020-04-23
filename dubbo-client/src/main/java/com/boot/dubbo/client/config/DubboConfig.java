package com.boot.dubbo.client.config;

import com.boot.dubbo.api.api.IProductService;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
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
    private ConsumerConfig consumerConfig;

    @Bean
    public IProductService productService() {

        ReferenceConfig<IProductService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(IProductService.class);
        referenceConfig.setConsumer(consumerConfig);
        return referenceConfig.get();
    }
}
