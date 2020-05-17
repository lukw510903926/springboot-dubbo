package com.boot.dubbo.finance.settlement;

import com.boot.dubbo.finance.dto.UserOrderDTO;
import com.boot.dubbo.finance.pay.OrderHandlerContext;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020/1/12 18:06
 */
public abstract class AbstractOrderSettlementHandler implements InitializingBean {

    /**
     * 处理订单结算
     *
     * @param userOrderDTO
     */
    public void handleOrderSettlement(UserOrderDTO userOrderDTO) {

        this.handleSeller(userOrderDTO);
        this.handlePlatform(userOrderDTO);
        this.handleDistribution(userOrderDTO);
        this.handleBank(userOrderDTO);
    }

    public void handleBank(UserOrderDTO userOrderDTO) {

    }

    public void handleSeller(UserOrderDTO userOrderDTO) {

    }

    public void handlePlatform(UserOrderDTO userOrderDTO) {

    }

    public void handleDistribution(UserOrderDTO userOrderDTO) {

    }

    @Override
    public void afterPropertiesSet() {
        OrderHandlerContext.SETTLE_HANDLER_MAP.put(getHandlerName(), this);
    }

    /**
     * 获取处理器的名称
     *
     * @return
     */
    public abstract String getHandlerName();
}
