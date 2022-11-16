package com.dubbo.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yangqi
 * @email : 13507615840@163.com
 * @description :
 * @since : 2022-11-16 22:12
 */
public interface BaseJdbcTemplateDao<T> {

    T selectById(Serializable id);

    List<T> selectList(Object param);

    Long getTotalCount(Object param);
}
