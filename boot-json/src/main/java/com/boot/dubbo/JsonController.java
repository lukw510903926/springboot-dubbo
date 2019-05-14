package com.boot.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.boot.dubbo.api.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-05-14 18:59
 * @email : lukewei@mockuai.com
 * @description :
 */
@Slf4j
@RestController
public class JsonController {

    @PostMapping("/json/request")
    public Object request(@RequestParam(value = "userName") String userName, @RequestBody User user) {

        log.info("userName :  {}", userName);
        log.info("user : {}", JSONObject.toJSONString(user));
        return user;
    }
}
