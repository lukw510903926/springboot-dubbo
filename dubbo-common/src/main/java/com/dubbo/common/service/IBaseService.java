package com.dubbo.common.service;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dubbo.common.util.ReflectionUtils;
import org.apache.commons.collections4.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @version V1.0
 * @Description: service 基础类
 * @author: yagnqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月11日 下午1:30:43
 */
public interface IBaseService<T> extends IService<T> {

    default boolean check(List<T> list, Serializable uid) {

        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        int size = list.size();
        if (size > 1) {
            return false;
        }
        T t = list.get(0);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(t.getClass());
        if (null == tableInfo || StringUtils.isEmpty(tableInfo.getKeyProperty())) {
            throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
        }
        String idProperty = tableInfo.getKeyProperty();
        String oid = ReflectionUtils.getter(t, idProperty).toString();
        return oid.equals(uid + "");
    }

    default List<T> list(T t) {
        return list(new QueryWrapper<>(t));
    }

    default T selectOne(T t) {

        return getOne(new QueryWrapper<>(t), true);
    }

    default boolean delete(T t) {

        return remove(new QueryWrapper<>(t));
    }

    default IPage<T> page(Page<T> page, T t) {

        if (ArrayUtils.isEmpty(page.descs()) && ArrayUtils.isEmpty(page.ascs())) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(t.getClass());
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyColumn())) {
                page.setDesc(tableInfo.getKeyColumn());
            }
        }
        return page(page, new QueryWrapper<>(t));
    }
}
