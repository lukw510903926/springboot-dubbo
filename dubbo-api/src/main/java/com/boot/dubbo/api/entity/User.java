package com.boot.dubbo.api.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 用户表
 */
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID , 这里故意演示注解可以无
	 */
	@TableId("test_id")
	private Long id;

	/**
	 * 名称 condition 注入查询条件【可无】
	 */
	@TableField(condition = SqlCondition.LIKE)
	private String name;

	private String userName;

	/**
	 * 这里故意演示注解可无
	 */
	@TableField("test_type")
	private Integer testType;

	/**
	 * 注入更新内容【可无】
	 */
	@TableField(update = "now()")
	private Date testDate;

	private Long role;
}
