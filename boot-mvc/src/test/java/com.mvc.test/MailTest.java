package com.mvc.test;

import com.boot.dubbo.mvc.DubboMVCApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2019/1/18 17:39
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DubboMVCApplication.class)
public class MailTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    @Test
    public void sendSimpleMail() {

        String sendTo = environment.getProperty("spring.mail.send.to");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("spring.mail.send.from"));
        message.setTo(sendTo.split(","));
        message.setSubject("主题：斑马认证失败");
        message.setText("斑马数据爬取失败,cookie 失效");
        mailSender.send(message);
    }
}