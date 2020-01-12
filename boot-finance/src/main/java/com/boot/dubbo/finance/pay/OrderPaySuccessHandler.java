package com.boot.dubbo.finance.pay;

import com.boot.dubbo.finance.dto.UserOrderDTO;

public class OrderPaySuccessHandler {

    public void handleOrderPaySuccess(UserOrderDTO userOrderDTO) {

        this.handleSeller(userOrderDTO);
        this.handlePlatform(userOrderDTO);
        this.handleDistribution(userOrderDTO);
    }

    public void handleSeller(UserOrderDTO userOrderDTO) {

    }

    public void handlePlatform(UserOrderDTO userOrderDTO) {

    }

    public void handleDistribution(UserOrderDTO userOrderDTO) {

    }
}
