package com.boot.dubbo.mvc.config;

import com.boot.dubbo.api.entity.Product;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.boot.dubbo.mvc.config.filter.PermissionFilter;
import com.boot.dubbo.mvc.config.interceptor.LogInterceptor;

/**
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-11 21:06
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor());
    }

    /**
     * 添加默认首页
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/template/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST","PUT","DELETE")
                .allowCredentials(true).maxAge(3600);
    }


    @Bean
    public Product product(){

        return new Product();
    }

    /**
     * 注册filter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<PermissionFilter> permissionFilter() {

        FilterRegistrationBean<PermissionFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new PermissionFilter());
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.addInitParameter("name", "value");//添加默认参数
        registration.setName("permissionFilter");
        registration.setOrder(2);//设置优先级
        return registration;
    }
}
