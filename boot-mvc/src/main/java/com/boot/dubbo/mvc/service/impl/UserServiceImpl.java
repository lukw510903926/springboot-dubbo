package com.boot.dubbo.mvc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.api.entity.User;
import com.boot.dubbo.api.mapper.UserMapper;
import com.boot.dubbo.mvc.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/18 14:15
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


}
