package com.dubbo.common.autoconfigure.oss;


/**  
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: yagnqi
 * @email : yangqi@ywwl.com 
 * @date:   2018年10月11日 下午6:43:57   
 * @version V1.0 
 */
public class OssProperties {

	private String endpoint;
	
	private String accessKeyId;
	
	private String secretAccessKey;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}
}


