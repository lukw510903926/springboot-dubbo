package com.dubbo.common.util.alibaba;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-12-13 19:21
 * @email : lukewei@mockuai.com
 * @description :
 */
@Data
public class LogHubProjectProperties implements Serializable {


    private static final long serialVersionUID = -5952997891481002674L;

    private String accessKeyId;

    private String accessKeySecret;

    private String project;

    private String endpoint;
}
