package com.dubbo.common;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author : yangqi
 * @email : 13507615840@163.com
 * @description :
 * @since : 2022-11-16 22:14
 */
public abstract class BaseJdbcTemplateDaoImpl<T> implements BaseJdbcTemplateDao<T> {

    private JdbcTemplate jdbcTemplate;

    protected abstract void setJdbcTemplate();

    @Override
    public T selectById(Serializable id) {
        String selectSQL = this.getSelectSQL();
        return this.jdbcTemplate.queryForObject(selectSQL, new Object[]{id}, this::mapToBean);
    }

    @Override
    public List<T> selectList(Object param) {
        String selectSQL = this.getSelectSQL();
        String where = this.getWhere(param);
        return this.jdbcTemplate.query(selectSQL + where, new Object[]{}, this::mapToBean);
    }

    @Override
    public Long getTotalCount(Object param) {
        String selectCountSQL = this.getCountSQL();
        String where = this.getWhere(param);
        return this.jdbcTemplate.queryForObject(selectCountSQL + where, new Object[]{}, Long.class);
    }

    protected abstract T mapToBean(ResultSet resultSet, Integer rowNum);

    protected abstract String getSelectSQL();

    protected abstract String getCountSQL();

    protected abstract String getWhere(Object param);
}
