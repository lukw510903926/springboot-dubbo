package com.boot.dubbo.finance.pay;

import com.boot.dubbo.finance.dto.UserOrderDTO;

public class OrderPaySuccessHandler {

    public void handleOrderPaySuccess(UserOrderDTO UserOrderDTO){

        this.handleSeller();
        this.handlePlatform();
        this.handleDistribution();
    }

    public void handleSeller(){

    }

    public void handlePlatform(){

    }

    public void handleDistribution(){

    }
}
