package com.dubbo.common.util;

import java.util.UUID;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 11:09
 **/
public class IdUtil {

    public static String uuid(){

        return UUID.randomUUID().toString().replace("-","");
    }
}
