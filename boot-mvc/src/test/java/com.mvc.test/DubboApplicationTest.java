package com.mvc.test;

import com.boot.dubbo.mvc.DubboMVCApplication;
import com.dubbo.common.autoconfigure.oss.OssProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 17:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboMVCApplication.class)
public class DubboApplicationTest {

    @Autowired
    private OssProperties ossProperties;

    @Test
    public void test(){

        System.out.println(ossProperties);

    }
}
