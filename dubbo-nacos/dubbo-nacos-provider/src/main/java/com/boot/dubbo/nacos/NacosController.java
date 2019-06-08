package com.boot.dubbo.nacos;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.boot.dubbo.nacos.config.NacosWeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-06-08 13:43
 * @email : lukewei@mockuai.com
 * @description :
 */
@Slf4j
@RestController
public class NacosController {

    @NacosValue(value = "${we.chat.notifyUrl:defaultValue}", autoRefreshed = true)
    private String notifyUrl ;

    @Autowired
    private NacosWeChatProperties weChatProperties;

    @GetMapping(value = "/nacos/value")
    public String value() {
        return notifyUrl;
    }

    @GetMapping(value = "/nacos/property")
    public String weChatProperties(){

        log.info("properties : {}",weChatProperties);
        return weChatProperties.getNotifyUrl();
    }
}
