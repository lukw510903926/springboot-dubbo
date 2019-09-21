package com.rest.dubbo.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.boot.dubbo.api.entity.User;

/**  
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: yagnqi
 * @email : yangqi@ywwl.com 
 * @date:   2018年10月10日 下午1:42:23   
 * @version V1.0 
 */
public class RestTest {

	public static void main(String[] args) {
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:2345/user/list";
		User user = new User();
		user.setName("张三");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> requestEntity = new HttpEntity<String>(JSONObject.toJSONString(user), headers);

		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, requestEntity, String.class);
		System.out.println(postForEntity.getBody());
	}
}


