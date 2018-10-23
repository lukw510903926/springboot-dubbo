package com.boot.dubbo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 *
 * @project : dubbo-client
 * @createTime : 2018年7月3日 : 上午10:15:50
 * @author : lukewei
 * @description :
 */

@SpringBootApplication
public class DubboClientApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(DubboClientApplication.class);

	public static void main(String[] args){
		SpringApplication.run(DubboClientApplication.class, args);
		logger.info("dubbo client application start successfully---------");
	}

	/**
	 *
	 * @param builder
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
}
