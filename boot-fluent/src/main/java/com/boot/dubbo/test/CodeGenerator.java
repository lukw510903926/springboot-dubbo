package com.boot.dubbo.test;

import cn.org.atool.generator.FileGenerator;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2023-09-01 16:46
 */
public class CodeGenerator {

    // 数据源 url
    static final String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
    // 数据库用户名
    static final String username = "root";
    // 数据库密码
    static final String password = "root";

    public static void main(String[] args) {
        FileGenerator.build(User.class);
    }

    @Tables(
            // 设置数据库连接信息
            url = url, username = username, password = password,
            // 设置entity类生成src目录, 相对于 user.dir
            srcDir = "src/main/java",
            // 设置entity类的package值
            basePack = "com.boot.dubbo.entity",
            // 设置dao接口和实现的src目录, 相对于 user.dir
            daoDir = "src/main/java",
            // 设置哪些表要生成Entity文件
            tables = {@Table(value = {"user_video_script"})})
    static class User { // 类名随便取, 只是配置定义的一个载体
    }

}
