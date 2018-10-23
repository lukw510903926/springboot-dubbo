package com.boot.dubbo.mvc.controller;

import com.dubbo.common.web.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/16 10:16
 **/
@RestController
@RequestMapping("/upload")
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/file")
    public RestResult<String> upload(HttpServletRequest request) {

        logger.info("request: {}", request);
        return RestResult.success("success");
    }
}
