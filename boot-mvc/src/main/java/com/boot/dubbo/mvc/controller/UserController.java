package com.boot.dubbo.mvc.controller;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.dubbo.mvc.service.IUserService;
import com.dubbo.common.web.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.boot.dubbo.api.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/list")
    public RestResult<List<User>> sayHello() {

        return RestResult.success(this.userService.list(new QueryWrapper<>()));
    }

    @GetMapping("/add")
    public RestResult<String> add(){

        for (int i = 0; i <20 ;i++){
            User user = new User();
            user.setTestDate(new Date());
            user.setName("name : "+i);
            user.setTestType(i);
            this.userService.saveOrUpdate(user);
        }
        return RestResult.success("success");
    }

    @GetMapping("/user/{userId}")
    public RestResult<User> info(@PathVariable("userId")String userId){

        return RestResult.success(this.userService.getById(userId));
    }

    @PostMapping("/page/list")
    public RestResult<IPage<User>> query(@RequestBody User user){
        logger.info("user: {}",user);
        Page<User> page = new Page<>(1,10);
        page.setDesc("test_id");
        return  RestResult.success(this.userService.page(page, user));
    }
}
