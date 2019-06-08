package com.boot.dubbo.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-06-08 13:59
 * @email : lukewei@mockuai.com
 * @description :
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "we.chat")
@NacosConfigurationProperties(dataId = "dubbo-nacos-provider", autoRefreshed = true)
public class NacosWeChatProperties {

    /**
     * appId
     */
    private String appId;

    /**
     *应用密钥
     */
    private String appSecret;

    /**
     * 商户Id
     */
    private String mchId;

    /**
     * 商户秘钥
     */
    private String mchKey;

    /**
     * 回调地址
     */
    private String notifyUrl;
}
