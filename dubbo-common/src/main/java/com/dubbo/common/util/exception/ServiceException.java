package com.dubbo.common.util.exception;


/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/18 14:10
 **/
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 错误消息
     */
    private String msg;

    public ServiceException() {
        this(1000, "操作失败");
    }

    public ServiceException(String message) {
        this(1000, message);
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.code = 1000;
        this.msg = message;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.code = 1000;
    }

    public int getCode() {
        return code;
    }

    public ServiceException setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ServiceException setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
