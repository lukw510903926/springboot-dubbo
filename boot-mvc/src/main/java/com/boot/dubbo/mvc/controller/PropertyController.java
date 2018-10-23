package com.boot.dubbo.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.dubbo.common.autoconfigure.sso.SsoProperties;
import com.dubbo.common.util.resdis.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/18 15:02
 **/
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private Environment environment;

    @Autowired
    private SsoProperties ssoProperties;

    @Autowired
    private OSS oSS;

    @Autowired
    private CacheService cacheService;

    @GetMapping("/property")
    public String property() {
        return this.ssoProperties.toString();
    }

    @GetMapping("/oSS")
    public String oSS() {
        return JSONObject.toJSONString(oSS);
    }

    @GetMapping("/redis")
    public String redis() {
        cacheService.set("spring.config.property",this.environment.getProperty("spring.config.property"));
        return cacheService.get("spring.config.property") + "";
    }
}
