package com.boot.dubbo.server;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @project : dubbo-server
 * @createTime : 2018年7月3日 : 上午9:33:27
 * @author : lukewei
 * @description :
 */
@SpringBootApplication
@MapperScan(basePackages = "com.boot.dubbo.api.mapper")
public class RestDubboApplication {

	private static Logger logger = LoggerFactory.getLogger(RestDubboApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(RestDubboApplication.class, args);
		logger.info("dubbo server application start successfully -------");
	}

}
