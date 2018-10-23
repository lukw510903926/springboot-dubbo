package com.boot.dubbo.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面模版
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-14 20:28
 **/

@Controller
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/index")
    public String index(){

        return "thymeleaf";
    }
}
