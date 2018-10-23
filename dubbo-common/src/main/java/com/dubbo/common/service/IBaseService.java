package com.dubbo.common.service;

import java.io.Serializable;
import java.util.List;

import com.dubbo.common.util.ReflectionUtils;
import org.apache.commons.collections4.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yagnqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月11日 下午1:30:43
 * @version V1.0
 */
public interface IBaseService<T> extends IService<T> {

	default boolean check(List<T> list, Serializable uid, String idProperty) {

		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		int size = list.size();
		if (size > 1) {
			return false;
		}
		T t = list.get(0);
		String oid = ReflectionUtils.getter(t, idProperty).toString();
		return oid.equals(uid);
	}
}
