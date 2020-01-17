package com.boot.dubbo.api.filter;

import java.lang.reflect.Method;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcResult;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/30 11:33
 **/
public class ProviderExceptionFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ProviderExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        this.logger.info(" ==Provider Exception Filter==");
        Result result = invoker.invoke(invocation);
        if (result.hasException() && GenericService.class != invoker.getInterface()) {
            try {
                Throwable exception = result.getException();
                if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                    return result;
                }
                Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                Class<?>[] exceptionClassses = method.getExceptionTypes();
                for (Class<?> exceptionClass : exceptionClassses) {
                    if (exception.getClass().equals(exceptionClass)) {
                        return result;
                    }
                }
                this.logger.error("dubbo provider has undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);

                String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                    return result;
                }
                String className = exception.getClass().getName();
                if (className.startsWith("java.") || className.startsWith("javax.")) {
                    return result;
                }
                if (exception instanceof RpcException) {
                    return result;
                }
                return new RpcResult(new RuntimeException(StringUtils.toString(exception)));
            } catch (Exception e) {
                this.logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
                return result;
            }
        }
        return result;
    }
}
