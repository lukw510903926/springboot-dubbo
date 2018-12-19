package com.dubbo.common.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 * 解决@requestBody 不能二次读取参数问题
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/18 17:30
 **/
public class ChannelFilter implements Filter {

    public static final String REQUEST_BODY = ChannelFilter.class.getName() + "request_body";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        BodyReaderHttpServletRequestWrapper requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest){
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }
        if(requestWrapper == null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            requestWrapper.setAttribute(REQUEST_BODY,requestWrapper.getBody());
            filterChain.doFilter(requestWrapper,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
