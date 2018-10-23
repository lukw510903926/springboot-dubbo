package com.dubbo.common.web;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/16 16:56
 **/
public class ResponseUtil {



    private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     *
     * @param response
     * @param status
     *            状态
     * @param msg
     *            消息
     */
    public static void writeErrorMsg(HttpServletResponse response, int status, String msg) {

        response.reset();
        RestResult<String> restResult = new RestResult<>();
        restResult.setCode(status);
        restResult.setMsg(msg);
        restResult.setSuccess(false);
        writeMsg(response, JSONObject.toJSONString(restResult));
    }

    /**
     *
     * @param response
     * @param status
     *            状态
     * @param msg
     *            消息
     * @param success
     *            请求是否成功
     */
    public static void writeMsg(HttpServletResponse response, int status, String msg, boolean success) {

        response.reset();
        response.setStatus(status);
        RestResult<String> restResult = new RestResult<>();
        restResult.setCode(status);
        restResult.setMsg(msg);
        restResult.setSuccess(success);
        writeMsg(response, JSONObject.toJSONString(restResult));
    }

    public static void writeUnAuthorizedMsg(HttpServletResponse response, String msg) {

        response.reset();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        RestResult<String> restResult = new RestResult<>();
        restResult.setCode(401);
        restResult.setMsg(msg);
        restResult.setSuccess(false);
        writeMsg(response, JSONObject.toJSONString(restResult));
    }

    public static void writeMsg(HttpServletResponse response, String restResult) {

        try {
            PrintWriter writer = response.getWriter();
            response.setContentType(DEFAULT_CONTENT_TYPE);
            writer.write(restResult);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error(" response write msg error : {}", e);
        }
    }
}
