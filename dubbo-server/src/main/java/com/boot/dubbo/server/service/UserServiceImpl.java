package com.boot.dubbo.server.service;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.api.api.IUserService;
import com.boot.dubbo.api.entity.User;
import com.boot.dubbo.api.mapper.UserMapper;
import com.dubbo.common.util.exception.ServiceException;

/**
 * @version V1.0
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yagnqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月10日 下午12:48:40
 */
@Service(version = "${demo.service.version}", filter = {"exceptionFilter", "providerExceptionFilter"})
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<User> findUser(User param) {

        return this.baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public User getUser() {

        User user = new User();
        user.setName("name");
        if (user.getId() == null) {
            throw new ServiceException("自定义异常");
        }
        return user;
    }


}
