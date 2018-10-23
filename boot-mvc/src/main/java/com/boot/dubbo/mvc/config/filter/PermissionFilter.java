package com.boot.dubbo.mvc.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * 权限过滤器
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-11 21:04
 **/

public class PermissionFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("permission filter : " + this.getClass());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
