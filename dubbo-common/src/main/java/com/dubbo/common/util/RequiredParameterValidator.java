package com.dubbo.common.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 必填参数校验
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-25 22:31
 **/

public class RequiredParameterValidator {

    /**
     * 指定属性校验必填
     *
     * @param object
     * @return
     */
    public static boolean validate(Object object) {

        StringBuffer stringBuffer = new StringBuffer();
        List<Field> fields = ReflectionUtils.getFields(object);
        List<String> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fields)) {
            fields.forEach(field -> {
                if (field.isAnnotationPresent(NotNull.class)) {
                    list.add(field.getName());
                }
            });
        }
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        return StringUtils.isEmpty(stringBuffer.toString());
    }

    /**
     * 指定属性校验必填
     *
     * @param object
     * @param list
     * @return
     */
    public static boolean validate(Object object, String... list) {

        StringBuffer stringBuffer = new StringBuffer();
        List<Field> fields = ReflectionUtils.getFields(object);
        Map<String, Field> fieldMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fields)) {
            fields.forEach(field -> fieldMap.put(field.getName(), field));
            Arrays.stream(list).forEach(key -> {
                if (fieldMap.containsKey(key)) {
                    Object value = ReflectionUtils.getFieldValue(object, key);
                    if (value == null) {
                        stringBuffer.append(key).append(" 不可为空 ! ");
                    }
                }
            });
        }
        return StringUtils.isEmpty(stringBuffer.toString());
    }
}
