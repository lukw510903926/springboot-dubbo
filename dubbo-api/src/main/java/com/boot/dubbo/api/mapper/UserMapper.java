package com.boot.dubbo.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.dubbo.api.entity.User;

/**  
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: yagnqi
 * @email : yangqi@ywwl.com 
 * @date:   2018年10月10日 上午10:18:21   
 * @version V1.0 
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义注入方法
	 */
	int deleteAll();

	@Select("select test_id as id, name, age, test_type from user")
	List<User> selectListBySQL();

}
