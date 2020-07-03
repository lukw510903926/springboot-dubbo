package com.boot.dubbo.gson;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConfigPostProcessor implements BeanPostProcessor {

    public static final Map<String,ConfigBean> CONFIG_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            ApolloValue annotation = field.getAnnotation(ApolloValue.class);
            if(annotation == null){
                continue;
            }
            String key = annotation.key();
            ConfigBean configBean = ConfigBean.build(key, field, bean);
            CONFIG_BEAN_MAP.put(key,configBean);
        }
        return bean;
    }
}
