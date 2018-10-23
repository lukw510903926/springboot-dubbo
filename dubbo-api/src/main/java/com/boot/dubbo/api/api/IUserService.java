package com.boot.dubbo.api.api;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.dubbo.api.entity.User;

public interface IUserService extends IService<User>{

	List<User> findUser(User user);

	User getUser();
}
