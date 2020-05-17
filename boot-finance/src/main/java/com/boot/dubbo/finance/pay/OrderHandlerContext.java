package com.boot.dubbo.finance.pay;

import com.boot.dubbo.finance.refund.AbstractOrderRefundHandler;
import com.boot.dubbo.finance.settlement.AbstractOrderSettlementHandler;

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

    public static final Map<String, AbstractOrderRefundHandler> REFUND_HANDLER_MAP = new ConcurrentHashMap<>(16);

    public static final Map<String, AbstractOrderSettlementHandler> SETTLE_HANDLER_MAP = new ConcurrentHashMap<>(16);

    public AbstractOrderPaySuccessHandler getPayHandler(String handlerName) {

        return PAY_HANDLER_MAP.get(handlerName);
    }

    public AbstractOrderRefundHandler getRefundHandler(String handlerName) {

        return REFUND_HANDLER_MAP.get(handlerName);
    }

    public AbstractOrderSettlementHandler getSettlementHandler(String handlerName) {

        return SETTLE_HANDLER_MAP.get(handlerName);
    }
}
