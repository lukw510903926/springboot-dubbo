package com.boot.dubbo.finance.pay;

import com.boot.dubbo.finance.constants.OrderHandlerEnum;
import com.boot.dubbo.finance.dto.UserOrderDTO;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 普通订单支付
 * @since : 2020-01-12 18:10
 */
public class NormalOrderPaySuccess extends AbstractOrderPaySuccessHandler {

    @Override
    public void handleOrderPaySuccess(UserOrderDTO userOrderDTO) {
        this.handleSeller(userOrderDTO);
    }

    @Override
    public void handleSeller(UserOrderDTO userOrderDTO) {
        super.handleSeller(userOrderDTO);
    }

    @Override
    public void handlePlatform(UserOrderDTO userOrderDTO) {
        super.handlePlatform(userOrderDTO);
    }

    @Override
    public String getHandlerName() {
        return OrderHandlerEnum.NORMAL_ORDER.name();
    }
}
