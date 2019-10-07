package com.boot.dubbo.gson;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class APIUtil {

    public static void main(String[] args) throws Exception {

        System.out.println("aa.com.bb.cc.com.dd".indexOf("com"));
        String resource = APIUtil.class.getClassLoader().getResource("").getPath();
        List<String> list = new ArrayList<>();
        getFile(resource, list);
        System.out.println(list);
    }

    public static void getFile(String path, List<String> list) throws Exception {

        File file = new File(path);
        File[] array = file.listFiles();
        if (array != null) {
            for (File temp : array) {
                if (temp.isFile()) {
                    list.add(temp.getName());
                    String tempPath = temp.getPath();
                    tempPath = tempPath.replace(File.separator, ".");
                    tempPath = tempPath.substring(tempPath.indexOf("com.")).replace(".class", "");
                    Class<?> aClass = Class.forName(tempPath);
                    if (aClass.isAnnotationPresent(RestController.class)) {
                        System.out.println(aClass);
                    }
                } else if (temp.isDirectory()) {
                    getFile(temp.getPath(), list);
                }
            }
        }
    }
}
