package com.boot.dubbo.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.dubbo.api.api.IUserService;
import com.boot.dubbo.api.entity.User;

/**  
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: yagnqi
 * @email : yangqi@ywwl.com 
 * @date:   2018年10月10日 上午11:30:48   
 * @version V1.0 
 */
@RestController
public class UserController {

	@Autowired
	private IUserService userServce;
	
	@GetMapping("/user/list")
	public List<User> findUser(){
		return this.userServce.findUser(new User());
	}
}


