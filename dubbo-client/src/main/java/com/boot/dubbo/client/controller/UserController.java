package com.boot.dubbo.client.controller;

import java.util.List;

import com.alibaba.dubbo.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.boot.dubbo.api.api.IUserService;
import com.boot.dubbo.api.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Reference(version = "1.0.0")
	private IUserService userService;

	@Autowired
	private ApplicationConfig applicationConfig;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/list")
	public List<User> sayHello() {

		User user = new User();
		user.setName("param");
		logger.info("applicationConfig :{}",applicationConfig);
		return this.userService.findUser(user);
	}

	@GetMapping("/info")
	public User getUser(){
		return  this.userService.getUser();
	}
}
