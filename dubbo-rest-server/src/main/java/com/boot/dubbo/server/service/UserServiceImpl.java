package com.boot.dubbo.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.api.api.IUserService;
import com.boot.dubbo.api.entity.User;
import com.boot.dubbo.api.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author : lukewei
 * @project : dubbo-rest-server
 * @createTime : 2018年7月3日 : 上午9:33:27
 * @description : @Consumes 请求参数的解析类型 @Produces 返回结果的类型
 */
@Slf4j
@Path("user")
@Consumes({ContentType.APPLICATION_JSON_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8})
@Service(version = "1.0.0", application = "${dubbo.application.id}", protocol = "${dubbo.protocol.name}", registry = "${dubbo.registry.id}")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @POST
    @Path("list")
    @Override
    public List<User> findUser(User param) {

        log.info(" dubbo post rest api param {}", param);
        return this.list(new QueryWrapper<>());
    }

    @GET
    @Path("one")
    @Override
    public User getUser() {
        log.info(" dubbo rest api ------------");
        return this.baseMapper.selectById(4);
    }
}