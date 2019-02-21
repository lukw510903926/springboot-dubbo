package com.boot.dubbo.mvc.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.boot.dubbo.api.entity.Product;
import com.dubbo.common.util.SpringApplicationContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Bean
    @ConditionalOnMissingBean
    public Product product(){

        System.out.println("============ConditionalOnMissingBean=============");
        Product product = new Product();
        product.setDescription("ConditionalOnMissingBean");
        return new Product();
    }

    @Bean
    public SpringApplicationContext applicationContext(){

        return new SpringApplicationContext();
    }
}
