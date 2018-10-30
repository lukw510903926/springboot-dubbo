package com.boot.dubbo.client.controller;

import java.util.List;

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

	@RequestMapping("/list")
	public List<User> sayHello() {
		
		User user = new User();
		user.setName("param");
		return this.userService.findUser(user);
	}

	@GetMapping("/info")
	public User getUser(){
		return  this.userService.getUser();
	}
}
