package com.boot.dubbo.weChat.config;

import com.dubbo.common.util.wechat.WeChatProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    @ConfigurationProperties(prefix = "we.chat")
    public WeChatProperties weChatProperties() {
        return new WeChatProperties();
    }
}
