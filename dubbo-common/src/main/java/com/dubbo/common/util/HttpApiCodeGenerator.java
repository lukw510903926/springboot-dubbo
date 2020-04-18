package com.dubbo.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class HttpApiCodeGenerator {

    private static final String TEST_PACKAGE_NAME = "com.mockuai.wdzg.adcenter.core";

    private static final String HTTP_API_PACKAGE = "com.mockuai.wdzg.adcenter.core.mop.api";

    private static final String SUFFIX = ".java";

    private static final String APP_KEY = "554992ad80486d1c518b47b69bfd4f66";

    private static final String USER_ID = "123456";

    private static final String API_HOST = "http://localhost:18891/";

    private static final List<String> IGNORE_PARAMETER = new ArrayList<>();

    static {
        IGNORE_PARAMETER.add("user_id");
        IGNORE_PARAMETER.add("app_key");
    }

    public static void main(String[] args) {

    }

    private static void buildCode() {

        try {
            String testRootPath = getTestRootPath();
            List<Class<?>> list = getClassList();
            list.forEach(entity -> buildCode(entity, testRootPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<Class<?>> getClassList() throws Exception {

        String resource = getRootPath() + "/src/main/java/" + (HTTP_API_PACKAGE.replace(".", File.separator));
        List<Class<?>> list = new ArrayList<>();
        getInstance(resource, list);
        return list;
    }

    private static void buildCode(Class<?> clazz, String rootPath) {

        Method[] methods = clazz.getMethods();
        String fileName = clazz.getSimpleName() + "Test";
        StringBuilder builder = new StringBuilder("package " + TEST_PACKAGE_NAME + ";\n\n");

        builder.append("import com.mockuai.framework.utils.OkHttpUtils;\n");
        builder.append("import com.alibaba.fastjson.JSON;\n");
        builder.append("import com.alibaba.fastjson.JSONObject;\n");
        builder.append("import org.junit.Test;\n");
        builder.append("import org.junit.Assert;\n");
        builder.append("import org.junit.Before;\n");
        builder.append("import java.util.Map; \n");
        builder.append("import java.util.HashMap; \n\n");
        builder.append("public class ").append(fileName).append(" { \n\n");
        builder.append("    private Map<String,String> param = new HashMap<>();\n\n");
        builder.append("    @Before\n");
        builder.append("    public void testBefore").append("(){ \n \n");
        builder.append("        param.put(\"app_key").append("\",\"").append(APP_KEY + "\");\n");
        builder.append("        param.put(\"user_id").append("\",\"").append(USER_ID + "\");\n");
        builder.append("    }\n\n");
        for (Method method : methods) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            String url = null;
            RequestMethod requestMethod = RequestMethod.GET;
            if (getMapping != null) {
                url = getMapping.value()[0];
                requestMethod = RequestMethod.GET;
            }
            if (url == null && method.isAnnotationPresent(PostMapping.class)) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                url = postMapping.value()[0];
                requestMethod = RequestMethod.POST;
            }
            if (url == null && method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                url = mapping.value()[0];
                requestMethod = mapping.method().length == 0 ? RequestMethod.GET : mapping.method()[0];
            }
            if (url == null) {
                continue;
            }
            builder.append("    @Test\n");
            Parameter[] parameters = method.getParameters();
            builder.append("    public void ").append(method.getName()).append("() throws Exception { \n \n");
            builder.append("        String apiPath = \"").append(API_HOST).append(url).append("\";\n");
            for (Parameter parameter : parameters) {
                String args = Optional.ofNullable(parameter.getAnnotation(RequestParam.class)).map(RequestParam::value).orElse("");
                args = StringUtils.isBlank(args) ? parameter.getName() : args;
                if (IGNORE_PARAMETER.contains(args)) {
                    continue;
                }
                builder.append("        param.put(\"").append(args).append("\",\"");

                if (Number.class.isAssignableFrom(parameter.getType())) {
                    builder.append(ThreadLocalRandom.current().nextInt(100, 20000)).append("\");");
                } else if (Date.class.isAssignableFrom(parameter.getType())) {
                    builder.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\");");
                } else {
                    builder.append(args).append("\");");
                }
                builder.append("\n");
            }
            builder.append("        String result = OkHttpUtils.");
            if (requestMethod == RequestMethod.GET) {
                builder.append("get");
            } else {
                builder.append("sendHttpPost");
            }
            builder.append("(apiPath,param);\n");
            builder.append("        System.out.println(result);\n");
            builder.append("        isSuccess(result);\n");
            builder.append("    }\n\n");
        }

        builder.append("    public static void isSuccess").append("(String result){ \n \n");
        builder.append("        JSONObject parse = JSON.parseObject(result, JSONObject.class);\n");
        builder.append("        Assert.assertEquals(\"10000\", parse.getString(\"code\"));\n");
        builder.append("    }\n");
        builder.append("\n}");
        System.out.println(builder);
        File file = new File(rootPath + File.separator + fileName + SUFFIX);
        try {
            FileUtils.writeStringToFile(file, builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTestRootPath() {
        String resource = getRootPath();
        return resource + "/src/test/java/" + (TEST_PACKAGE_NAME.replace(".", File.separator));
    }

    private static String getRootPath() {

        String path = HttpApiCodeGenerator.class.getClassLoader().getResource(".").getPath();
        return path.substring(0, path.indexOf("/target"));
    }

    private static void getInstance(String path, List<Class<?>> list) throws Exception {

        File file = new File(path);
        File[] array = file.listFiles();
        if (array != null) {
            for (File temp : array) {
                if (temp.isFile()) {
                    String tempPath = temp.getPath();
                    tempPath = tempPath.replace(File.separator, ".");
                    tempPath = tempPath.substring(tempPath.indexOf("com.")).replace(".java", "");
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