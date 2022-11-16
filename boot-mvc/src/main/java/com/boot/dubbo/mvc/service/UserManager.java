package com.boot.dubbo.mvc.service;

import com.boot.dubbo.api.entity.User;
import com.dubbo.common.BaseJdbcTemplateDaoImpl;

import java.sql.ResultSet;

/**
 * @author : yangqi
 * @email : 13507615840@163.com
 * @description :
 * @since : 2022-11-16 22:01
 */
public class UserManager extends BaseJdbcTemplateDaoImpl<User> {

    @Override
    protected void setJdbcTemplate() {

    }

    @Override
    protected User mapToBean(ResultSet resultSet, Integer rowNum) {
        return null;
    }

    @Override
    protected String getSelectSQL() {
        return null;
    }

    @Override
    protected String getWhere(Object param) {
        return null;
    }

    @Override
    protected String getCountSQL() {
        return null;
    }
}
