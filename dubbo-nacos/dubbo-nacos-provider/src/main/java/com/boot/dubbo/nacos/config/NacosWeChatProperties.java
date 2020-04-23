package com.boot.dubbo.nacos.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-06-08 13:59
 * @email : lukewei@mockuai.com
 * @description :
 */
@Data
@Configuration
public class NacosWeChatProperties {

    /**
     * appId
     */
    @NacosValue(value = "${we.chat.appId}", autoRefreshed = true)
    private String appId;

    /**
     *应用密钥
     */
    @NacosValue(value = "${we.chat.appSecret:defaultValue}", autoRefreshed = true)
    private String appSecret;

    /**
     * 商户Id
     */
    @NacosValue(value = "${we.chat.mchId}", autoRefreshed = true)
    private String mchId;

    /**
     * 商户秘钥
     */
    @NacosValue(value = "${we.chat.mchKey}", autoRefreshed = true)
    private String mchKey;

    /**
     * 回调地址
     */
    @NacosValue(value = "${we.chat.notifyUrl}", autoRefreshed = true)
    private String notifyUrl;
}
