package com.dubbo.common.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/16 16:38
 **/
public class WebUtils extends org.springframework.web.util.WebUtils {

    /**
     * 获取请求 request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取请求response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取session中的属性值
     *
     * @param sessionKey session中value的key
     * @return
     */
    public static Object getSessionAttribute(String sessionKey) {

        return getSessionAttribute(getRequest(), sessionKey);
    }

    /**
     * session保存值
     *
     * @param sessionKey
     * @param value
     */
    public static void setSessionAttribute(String sessionKey, Object value) {

        setSessionAttribute(getRequest(), sessionKey, value);
    }

    /**
     * 获取请求头
     *
     * @param headerName 请求头名称
     * @return
     */
    public static String getHeader(String headerName) {

        return getRequest().getHeader(headerName);
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    public static Map<String, Object> getRequestParam() {

        String paramName;
        HttpServletRequest request = getRequest();
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> enumPks = request.getParameterNames();
        while (enumPks.hasMoreElements()) {
            paramName = enumPks.nextElement();
            map.put(paramName, request.getParameter(paramName));
        }
        return map;
    }
}
