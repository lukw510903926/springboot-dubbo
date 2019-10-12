package com.boot.dubbo.gson;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class APIUtil {

    public static void main(String[] args) throws Exception {

        String resource = APIUtil.class.getClassLoader().getResource("").getPath();
        List<Class<?>> list = new ArrayList<>();
        getInstance(resource, list);
        list.forEach(APIUtil::buildCode);
    }

    private static void buildCode(Class<?> clazz) {

        Method[] methods = clazz.getMethods();
        StringBuilder builder = new StringBuilder("\nimport org.junit.Test;");
        builder.append("\nimport java.util.Map; ");
        builder.append("\nimport java.util.HashMap; \n\n");
        builder.append("public class ").append(clazz.getSimpleName()).append("Test {");
        for (Method method : methods) {
            GetMapping annotation = method.getAnnotation(GetMapping.class);
            if (annotation == null) {
                continue;
            }
            builder.append("\n\n    @Test\n");
            Parameter[] parameters = method.getParameters();
            builder.append("    public void ").append(method.getName()).append("(){ \n \n");
            builder.append("        String apiPath = \"").append(annotation.value()[0]).append("\";\n");
            builder.append("        Map<String,Object> param = new HashMap<>(); \n");
            for (Parameter parameter : parameters) {
                builder.append("        param.put(\"").append(parameter.getName()).append("\",\"");
                if (Number.class.isAssignableFrom(parameter.getType())) {
                    builder.append(ThreadLocalRandom.current().nextInt(100, 20000)).append("\");");
                } else if (Date.class.isAssignableFrom(parameter.getType())) {
                    builder.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\");");
                } else {
                    builder.append(parameter.getName()).append("\");");
                }
                builder.append("\n");
            }
            builder.append("    }");
        }
        builder.append("\n}");
        System.out.println(builder);
    }

    public static void getInstance(String path, List<Class<?>> list) throws Exception {

        File file = new File(path);
        File[] array = file.listFiles();
        if (array != null) {
            for (File temp : array) {
                if (temp.isFile()) {
                    String tempPath = temp.getPath();
                    tempPath = tempPath.replace(File.separator, ".");
                    tempPath = tempPath.substring(tempPath.indexOf("com.")).replace(".class", "");
                    Class<?> aClass = Class.forName(tempPath);
                    if (aClass.isAnnotationPresent(RestController.class)) {
                        list.add(aClass);
                    }
                } else if (temp.isDirectory()) {
                    getInstance(temp.getPath(), list);
                }
            }
        }
    }
}
