package com.dubbo.common.util.exception;

/**
 * 参数异常
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-25 22:30
 **/

public class ParameterException extends RuntimeException {

    /**
     * 状态码
     */
    private int code;

    /**
     * 错误消息
     */
    private String msg;

    public ParameterException(String message) {
        this(1000, message);
    }

    public ParameterException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
        this.code = 1000;
        this.msg = message;
    }

    public ParameterException(Throwable cause) {
        super(cause);
        this.code = 1000;
    }

    public int getCode() {
        return code;
    }

    public ParameterException setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ParameterException setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
