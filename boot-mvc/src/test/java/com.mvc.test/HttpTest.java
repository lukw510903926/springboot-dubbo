package com.mvc.test;
import com.boot.dubbo.api.entity.User;
import com.dubbo.common.util.http.RestHttpClient;
import org.junit.Test;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 17:17
 **/
public class HttpTest {

    @Test
    public void getRequest(){
        String result = RestHttpClient.get("http://localhost:8050/user/list", String.class);
        System.out.println(result);
    }

    @Test
    public void post(){

        User user = new User();
        System.out.println(RestHttpClient.post("http://localhost:8050/user/page/list",String.class,user));
    }
}
