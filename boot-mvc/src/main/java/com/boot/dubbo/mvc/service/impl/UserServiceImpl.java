package com.boot.dubbo.mvc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.api.entity.Product;
import com.boot.dubbo.api.entity.User;
import com.boot.dubbo.api.mapper.UserMapper;
import com.boot.dubbo.mvc.service.IProductService;
import com.boot.dubbo.mvc.service.IUserService;
import com.dubbo.common.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Autowired
    private IProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult<User> saveUser(User entity) {

        User user = new User();
        user.setName("test_user_product");
        user.setTestDate(new Date());
        super.save(user);
        if (productService.saveProduct(new Product())) {
            return RestResult.success(user);
        } else {
            return RestResult.fail(user, "保存失败");
        }
    }
}
