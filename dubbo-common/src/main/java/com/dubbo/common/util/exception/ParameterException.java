package com.dubbo.common.util.exception;

/**
 * 参数异常
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-25 22:30
 **/

public class ParameterException extends RuntimeException {

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

    protected ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
