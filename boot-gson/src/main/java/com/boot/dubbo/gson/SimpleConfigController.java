package com.boot.dubbo.gson;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;

@RestController
public class SimpleConfigController {

    @Resource
    private PersonConfig personConfig;

    @GetMapping("/apollo/config")
    public Object setValue(String key,String value){

        Map<String, ConfigBean> configBeanMap = ConfigPostProcessor.CONFIG_BEAN_MAP;
        if(configBeanMap.containsKey(key)){
            ConfigBean configBean = configBeanMap.get(key);
            Object bean = configBean.getBean();
            Field field = configBean.getField();
            ReflectionUtils.setField(field,bean,value);
        }
        return personConfig;
    }


    @GetMapping("/apollo/config/value")
    public Object getValue(){

        return personConfig;
    }
}
