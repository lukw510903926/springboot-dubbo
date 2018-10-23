package com.dubbo.common.autoconfigure.sso;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version V1.0
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yagnqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月11日 下午7:23:33
 */
@Configuration
@ConditionalOnProperty(prefix = "ywwl.sso", name = "ssoUrl",matchIfMissing = true)
public class SsoAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "ywwl.sso")
    public SsoProperties ssoProperties() {
        return new SsoProperties();
    }
}
