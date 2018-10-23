package com.dubbo.common.autoconfigure.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;

/**
 * @version V1.0
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yagnqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月11日 下午6:50:34
 */
@Configuration
@ConditionalOnClass(OSSClient.class)
@ConditionalOnProperty(prefix = "ali.oss", name = "endpoint")
public class OssAutoConfiguration {

    @Bean
    public OSS client() {
        OssProperties ossProperties = ossProperties();
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
                ossProperties.getSecretAccessKey());
    }

    @Bean
    @ConfigurationProperties(prefix = "ali.oss")
    public OssProperties ossProperties() {
        return new OssProperties();
    }
}
