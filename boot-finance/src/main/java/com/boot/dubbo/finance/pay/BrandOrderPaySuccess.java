package com.boot.dubbo.finance.pay;

import com.boot.dubbo.finance.constants.OrderHandlerEnum;
import com.boot.dubbo.finance.dto.UserOrderDTO;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-01-12 18:22
 */
public class BrandOrderPaySuccess extends AbstractOrderPaySuccessHandler {

    @Override
    public void handleOrderPaySuccess(UserOrderDTO userOrderDTO) {
        super.handleOrderPaySuccess(userOrderDTO);
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
    public void handleDistribution(UserOrderDTO userOrderDTO) {
        super.handleDistribution(userOrderDTO);
    }

    @Override
    public String getHandlerName() {
        return OrderHandlerEnum.BRAND_ORDER.name();
    }
}
