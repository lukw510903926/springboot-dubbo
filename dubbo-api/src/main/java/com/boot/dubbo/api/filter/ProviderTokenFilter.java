package com.boot.dubbo.api.filter;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.boot.dubbo.api.constants.DubboContants;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/19 16:37
 **/
@Slf4j
public class ProviderTokenFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String token = RpcContext.getContext().getAttachment(DubboContants.DUBBO_TOKEN);
        if (StringUtils.isBlank(token)) {
            Class<?> serviceType = invoker.getInterface();
            throw new RpcException("Invalid token! Forbid invoke remote service " + serviceType + " method " + invocation.getMethodName() + "() from consumer " + RpcContext.getContext().getRemoteHost() + " to provider " + RpcContext.getContext().getLocalHost());
        }
        log.info("provider token filter : {}", token);
        return invoker.invoke(invocation);
    }
}
