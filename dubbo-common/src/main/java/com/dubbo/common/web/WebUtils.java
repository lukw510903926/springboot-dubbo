package com.dubbo.common.web;

import com.alibaba.fastjson.JSONObject;
import com.dubbo.common.util.exception.ServiceException;
import com.dubbo.common.web.filter.ChannelFilter;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/16 16:38
 **/
public class WebUtils extends org.springframework.web.util.WebUtils {

    private static final String SESSION_LOGIN_USER = "_session_login_user";

    /**
     * 获取请求 request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) Optional.ofNullable(RequestContextHolder.getRequestAttributes()).orElseThrow(ServiceException::new)).getRequest();
    }

    /**
     * 获取请求response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {

        return ((ServletRequestAttributes) Optional.ofNullable(RequestContextHolder.getRequestAttributes()).orElseThrow(ServiceException::new)).getResponse();
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
     * 设置登录用户
     *
     * @param loginUser
     */
    public static void setLoginUser(LoginUser loginUser) {

        setSessionAttribute(SESSION_LOGIN_USER, loginUser);
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    public static LoginUser getLoginUser() {

        return (LoginUser) getSessionAttribute(SESSION_LOGIN_USER);
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

        //@requestBody 注解接收参数处理 需注册ChannelFilter
        if (MapUtils.isEmpty(map)) {
            Object requestBody = request.getAttribute(ChannelFilter.REQUEST_BODY);
            return Optional.ofNullable(requestBody).map(body -> JSONObject.parseObject(body.toString())).map(JSONObject::getInnerMap).orElse(map);
        }
        return map;
    }
}
