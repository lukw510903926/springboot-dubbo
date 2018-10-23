package com.boot.dubbo.api.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 用户表
 */
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

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTestType() {
		return this.testType;
	}

	public void setTestType(Integer testType) {
		this.testType = testType;
	}

	public Long getRole() {
		return this.role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	@Override
	public String toString() {
		return "User [id=" + this.getId() + ", name=" + name + ", testType=" + testType + ", testDate=" + testDate
				+ ", role=" + role + "]";
	}

}
