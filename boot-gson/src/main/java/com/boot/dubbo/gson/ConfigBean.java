package com.boot.dubbo.gson;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ConfigBean {

    private String key;

    private Field field;

    private Object bean;

    public static ConfigBean build(String key, Field field, Object bean) {
        ConfigBean configBean = new ConfigBean();
        configBean.setBean(bean);
        configBean.setField(field);
        configBean.setKey(key);
        return configBean;
    }
}
