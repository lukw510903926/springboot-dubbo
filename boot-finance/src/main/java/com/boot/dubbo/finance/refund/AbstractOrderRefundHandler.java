package com.boot.dubbo.finance.refund;

import com.boot.dubbo.finance.dto.UserOrderDTO;
import com.boot.dubbo.finance.pay.OrderHandlerContext;
import com.boot.dubbo.finance.refund.command.DepositAccountRefundCommand;
import com.boot.dubbo.finance.refund.command.FinanceAccountRefundCommand;
import com.boot.dubbo.finance.refund.command.OweAccountRefundCommand;
import com.boot.dubbo.finance.refund.command.RefundDTO;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020/1/12 18:06
 */
public abstract class AbstractOrderRefundHandler implements InitializingBean {

    /**
     * 处理订单退款
     *
     * @param userOrderDTO
     */
    public void handleOrderRefund(UserOrderDTO userOrderDTO) {

        this.handleSeller(userOrderDTO);
        this.handlePlatform(userOrderDTO);
        this.handleDistribution(userOrderDTO);
        this.handleBank(userOrderDTO);
    }

    public void handleBank(UserOrderDTO userOrderDTO) {

    }

    public void handleSeller(UserOrderDTO userOrderDTO) {

        FinanceAccountRefundCommand financeAccountRefundCommand = new FinanceAccountRefundCommand();
        DepositAccountRefundCommand depositAccountRefundCommand = new DepositAccountRefundCommand();
        OweAccountRefundCommand oweAccountRefundCommand = new OweAccountRefundCommand();
        financeAccountRefundCommand.setNextCommand(depositAccountRefundCommand);
        depositAccountRefundCommand.setNextCommand(oweAccountRefundCommand);

        financeAccountRefundCommand.execute(new RefundDTO());
    }

    public void handlePlatform(UserOrderDTO userOrderDTO) {

    }

    public void handleDistribution(UserOrderDTO userOrderDTO) {

    }

    @Override
    public void afterPropertiesSet() {
        OrderHandlerContext.REFUND_HANDLER_MAP.put(getHandlerName(), this);
    }

    /**
     * 获取处理器的名称
     *
     * @return
     */
    public abstract String getHandlerName();
}
