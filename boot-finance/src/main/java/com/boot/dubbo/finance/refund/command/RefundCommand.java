package com.boot.dubbo.finance.refund.command;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-07-18 23:34
 */
public interface RefundCommand {

    /**
     * 设置下一个指令
     *
     * @param command
     */
    void setNextCommand(RefundCommand command);

    /**
     * 执行指令
     *
     * @param refundDTO
     */
    void execute(RefundDTO refundDTO);
}
