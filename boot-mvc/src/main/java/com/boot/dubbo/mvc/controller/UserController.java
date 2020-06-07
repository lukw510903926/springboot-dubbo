package com.boot.dubbo.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.dubbo.api.entity.Product;
import com.boot.dubbo.api.entity.User;
import com.boot.dubbo.mvc.service.IUserService;
import com.dubbo.common.util.SpringApplicationContext;
import com.dubbo.common.util.annotation.HttpApi;
import com.dubbo.common.web.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    @HttpApi(url = "/user/list", method = "get", upload = true)
    public RestResult<List<User>> sayHello() {

        return RestResult.success(this.userService.list(new QueryWrapper<>()));
    }

    /**
     * 错误 demo
     *
     * @return
     */
    @RequestMapping("/saveUser")
    public RestResult<User> saveUser() {

        return this.userService.saveUser(new User());
    }

    /**
     * 正确demo
     *
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public RestResult<User> saveOrUpdate() {

        User user = new User();
        user.setName("user_product_name");
        user.setTestDate(new Date());
        this.userService.saveOrUpdate(user);
        return RestResult.success(user);
    }


    @GetMapping("/add")
    public RestResult<String> add() {

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setTestDate(new Date());
            user.setName("name : " + i);
            user.setTestType(i);
            this.userService.saveOrUpdate(user);
        }
        return RestResult.success("success");
    }

    @GetMapping("/user/{userId}")
    public RestResult<User> info(@PathVariable("userId") String userId) {

        return RestResult.success(this.userService.getById(userId));
    }

    @GetMapping("/cache/{userId}")
    public RestResult<User> findById(@PathVariable Long userId) {

        User user = new User();
        user.setId(userId);
        return RestResult.success(this.userService.findById(user));
    }

    @PostMapping("/page/list")
    public RestResult<IPage<User>> query(@RequestBody User user) {
        log.info("user: {}", user);
        Page<User> page = new Page<>(1, 10);
        page.setDesc("test_id");
        return RestResult.success(this.userService.page(page, user));
    }

    @GetMapping("/product")
    public String product() {
        return JSON.toJSONString(SpringApplicationContext.getBean(Product.class));
    }
}
