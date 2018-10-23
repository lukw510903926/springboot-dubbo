package com.dubbo.common.web;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/16 16:30
 **/
public class RestResult<T> implements Serializable {


    private static final long serialVersionUID = 1321730040385604286L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 数据
     */
    private T data;

    /**
     * 消息
     */
    private String msg;

    /**
     * 请求成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> success(T data) {

        RestResult<T> restResult = new RestResult<>();
        restResult.setSuccess(true);
        restResult.setData(data);
        restResult.setCode(200);
        restResult.setMsg("请求成功");
        return restResult;
    }

    /**
     * 请求失败
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> fail(T data, String msg) {

        RestResult<T> restResult = new RestResult<>();
        restResult.setSuccess(false);
        restResult.setData(data);
        restResult.setCode(100);
        msg = StringUtils.isBlank(msg) ? "请求失败" : msg;
        restResult.setMsg(msg);
        return restResult;
    }

    /**
     * 请求失败
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> fail(T data,Integer code, String msg) {

        RestResult<T> restResult = new RestResult<>();
        restResult.setSuccess(false);
        restResult.setData(data);
        restResult.setCode(code);
        msg = StringUtils.isBlank(msg) ? "请求失败" : msg;
        restResult.setMsg(msg);
        return restResult;
    }

    /**
     * 请求失败
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> parameter(T data, String msg) {

        RestResult<T> restResult = new RestResult<>();
        restResult.setSuccess(false);
        restResult.setData(data);
        restResult.setCode(300);
        msg = StringUtils.isBlank(msg) ? "请求失败" : msg;
        restResult.setMsg(msg);
        return restResult;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
