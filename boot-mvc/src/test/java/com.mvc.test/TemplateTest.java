package com.mvc.test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/24 11:05
 **/
public class TemplateTest {

    public static void main(String[] args) {

        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ss").format(ZonedDateTime.now()));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ss").format(LocalDateTime.now()));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm ss")));
        System.out.println(ZonedDateTime.now());
        System.out.println(LocalDateTime.now());
    }
}
