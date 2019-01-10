package com.boot.dubbo.mvc.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * @Description
 * </p>
 * @author yangqi
 * @since 2018/10/12 15:17
 * @email yangqi@ywwl.com
 **/
@Configuration
public class ApplicationConfig {

    /**
     * 分页拦截器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
