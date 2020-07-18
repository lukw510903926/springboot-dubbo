package com.boot.dubbo.finance.refund.command;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-07-18 23:38
 */
public abstract class AbstractRefundCommand implements RefundCommand {

    RefundCommand nextCommand;

    @Override
    public void setNextCommand(RefundCommand command) {
        this.nextCommand = command;
    }

    @Override
    public void execute(RefundDTO refundDTO) {
        if (this.nextCommand != null) {
            nextCommand.execute(refundDTO);
        }
    }
}
