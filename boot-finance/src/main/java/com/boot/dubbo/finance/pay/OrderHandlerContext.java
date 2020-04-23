package com.boot.dubbo.finance.pay;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-01-12 18:16
 */
public class OrderHandlerContext {

    public static final Map<String, AbstractOrderPaySuccessHandler> PAY_HANDLER_MAP = new ConcurrentHashMap<>(16);

    public AbstractOrderPaySuccessHandler getHandler(String handlerName) {

        return PAY_HANDLER_MAP.get(handlerName);
    }
}
