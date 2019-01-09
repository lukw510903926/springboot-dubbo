package com.boot.dubbo.mvc.config.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 权限过滤器
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-11 21:04
 **/
@Slf4j
public class PermissionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
