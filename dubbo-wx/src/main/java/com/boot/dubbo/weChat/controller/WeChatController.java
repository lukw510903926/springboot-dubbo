package com.boot.dubbo.weChat.controller;


import com.boot.dubbo.weChat.service.WeChatService;
import com.dubbo.common.util.wechat.WeChatUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 11:00
 **/
@Slf4j
@Controller
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    @RequestMapping("/test")
    public String test(Model model, HttpServletRequest request) {

        Map<String, String> result = weChatService.create("1", new WeChatUser(), request);
        log.info("result : {}", result);
        model.addAttribute("appId", result.get("appId"));
        model.addAttribute("timeStamp", result.get("timeStamp"));
        model.addAttribute("nonceStr", result.get("nonceStr"));
        model.addAttribute("package", result.get("package"));
        model.addAttribute("signType", result.get("signType"));
        model.addAttribute("paySign", result.get("paySign"));
        return "pay_test";
    }

    @RequestMapping("/notify")
    public void wxPayNotify(HttpServletRequest request) {
        weChatService.wxPayNotify(request);
    }

}
