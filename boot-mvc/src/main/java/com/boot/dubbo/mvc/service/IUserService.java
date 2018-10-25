package com.boot.dubbo.mvc.service;

import com.boot.dubbo.api.entity.User;
import com.dubbo.common.service.IBaseService;
import com.dubbo.common.web.RestResult;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/18 14:14
 **/
public interface IUserService extends IBaseService<User> {

    RestResult<User> saveUser(User user);
}
