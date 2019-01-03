package com.boot.dubbo.nacos.config;

import com.dubbo.common.util.weChat.WeChatProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2019/1/3 15:02
 **/
@Configuration
public class NacosConfig {

    @Bean
    @ConfigurationProperties(prefix = "we.chat")
    public WeChatProperties weChatProperties(){

        return  new WeChatProperties();
    }
}
