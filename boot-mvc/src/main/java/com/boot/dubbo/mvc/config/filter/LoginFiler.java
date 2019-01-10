package com.boot.dubbo.mvc.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录拦截 注解的方式注册到server
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-11 20:47
 **/
@Slf4j
@Order(1)
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFiler implements Filter {

    private static final String AUTH_HEADER = "boot_dubbo_auth_header";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("{} : {}", AUTH_HEADER, request.getHeader(AUTH_HEADER));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
