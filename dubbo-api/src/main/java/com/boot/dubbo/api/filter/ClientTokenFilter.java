package com.boot.dubbo.api.filter;

import com.alibaba.dubbo.rpc.*;
import com.boot.dubbo.api.constants.DubboContants;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/19 16:04
 **/
@Slf4j
public class ClientTokenFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation){
        log.info("---------------------client tokenFilter---------------------------------------");
        RpcContext.getContext().setAttachment(DubboContants.DUBBO_TOKEN,DubboContants.DUBBO_TOKEN);
        return invoker.invoke(invocation);
    }
}
