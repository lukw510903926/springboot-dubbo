package com.dubbo.common.util;

import com.dubbo.common.util.annotation.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
     * 获取object对象属性中注解NotNull.class的并校验必填
     *
     * @param object 校验对象
     * @return
     */
    public static boolean validate(Object object) {

        List<Field> fields = ReflectionUtils.getFields(object);
        List<String> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fields)) {
            fields.forEach(field -> {
                if (field.isAnnotationPresent(NotNull.class)) {
                    list.add(field.getName());
                }
            });
        }
        return validate(object, list);
    }

    /**
     * 指定属性校验必填
     *
     * @param object 校验对象
     * @param list   属性列表
     * @return
     */
    public static boolean validate(Object object, String... list) {

        return validate(object, Arrays.asList(list));
    }

    /**
     * @param object 校验对象
     * @param list   属性列表
     * @return
     */
    public static boolean validate(Object object, List<String> list) {

        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        StringBuffer stringBuffer = new StringBuffer();
        List<Field> fields = ReflectionUtils.getFields(object);
        Map<String, Field> fieldMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fields)) {
            fields.forEach(field -> fieldMap.put(field.getName(), field));
            list.forEach(key -> {
                if (fieldMap.containsKey(key)) {
                    Object value = ReflectionUtils.getFieldValue(object, key);
                    if (value == null || StringUtils.isEmpty(value.toString())) {
                        NotNull notNull = fieldMap.get(key).getAnnotation(NotNull.class);
                        String property = StringUtils.isBlank(notNull.value()) ? key : notNull.value();
                        stringBuffer.append(property).append(" 不可为空 ! ");
                    }
                }
            });
        }
        return StringUtils.isEmpty(stringBuffer);
    }
}
