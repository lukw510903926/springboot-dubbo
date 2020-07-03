package com.boot.dubbo.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-06-07 21:02
 */
@Slf4j
@Component
public class HttpApiPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        RestController annotation = bean.getClass().getAnnotation(RestController.class);
        if (annotation == null) {
            return bean;
        }
        RequestMapping requestMapping = bean.getClass().getAnnotation(RequestMapping.class);
        String baseUrl = Optional.ofNullable(requestMapping).map(RequestMapping::value).map(item -> item[0]).orElse("");
        Method[] methods = bean.getClass().getMethods();
        Arrays.stream(methods).forEach(method -> {
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            if (methodAnnotation != null) {
                String methodUrl = methodAnnotation.value()[0];
                RequestMethod[] requestMethods = methodAnnotation.method();
                String httpMethod = ArrayUtils.isEmpty(requestMethods) ? "get" : requestMethods[0].toString();
                log.info("url : {} httpMethod {}", baseUrl + methodUrl, httpMethod);
            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
