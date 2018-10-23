package com.dubbo.common.autoconfigure.sso;

import java.io.Serializable;

/**  
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: yagnqi
 * @email : yangqi@ywwl.com 
 * @date:   2018年10月11日 下午7:18:22   
 * @version V1.0 
 */
public class SsoProperties implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * sso服务器地址
	 */
	private String ssoUrl;
	
	/**
	 * 接入项目编码
	 */
	private String projectCode;
	
	/**
	 * 接入项目名称
	 */
	private String projectName;

	public String getSsoUrl() {
		return ssoUrl;
	}

	public void setSsoUrl(String ssoUrl) {
		this.ssoUrl = ssoUrl;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "SsoProperties [ssoUrl=" + ssoUrl + ", projectCode=" + projectCode + ", projectName=" + projectName
				+ "]";
	}
}


