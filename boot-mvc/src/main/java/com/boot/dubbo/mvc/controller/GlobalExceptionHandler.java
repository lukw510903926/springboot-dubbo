package com.boot.dubbo.mvc.controller;

import java.util.Map;

import com.dubbo.common.util.exception.ParameterException;
import com.dubbo.common.util.exception.ServiceException;
import com.dubbo.common.web.RestResult;
import com.dubbo.common.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * <p>
 * 统一异常处理
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/16 10:16
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 所有异常报错
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RestResult<Map<String, Object>> allExceptionHandler(Exception exception) {

        Map<String, Object> map = WebUtils.getRequestParam();
        logger.error("统一异常处理 :参数 : {},异常信息 : {}", map, exception);
        return RestResult.fail(map, "操作失败!");
    }


    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public RestResult<Map<String,Object>> serviceException(ServiceException exception){

        Map<String, Object> params = WebUtils.getRequestParam();
        logger.error("系统异常 : {}",exception);
        return RestResult.fail(params,exception.getCode(),exception.getMsg());
    }


    @ResponseBody
    @ExceptionHandler(ParameterException.class)
    public RestResult<Map<String,Object>> serviceException(ParameterException exception){

        Map<String, Object> params = WebUtils.getRequestParam();
        logger.error("参数异常 : {}",exception);
        return RestResult.fail(params,exception.getCode(),exception.getMsg());
    }

    /**
     * 附件大小异常
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public RestResult<Map<String, Object>> maxUploadSizeException(MaxUploadSizeExceededException exception) {

        Map<String, Object> map = WebUtils.getRequestParam();
        String msg = "可以上传附件最大值 : " + exception.getMaxUploadSize() / 1024 / 1024 + "M";
        logger.error("统一异常处理 :参数 : {},异常信息 : {}", map, exception);
        return RestResult.parameter(map, msg);
    }
}
