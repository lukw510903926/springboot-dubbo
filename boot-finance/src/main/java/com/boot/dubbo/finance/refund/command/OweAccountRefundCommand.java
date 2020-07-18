package com.boot.dubbo.finance.refund.command;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 欠款账户执行退款
 * @since : 2020-07-18 23:41
 */
public class OweAccountRefundCommand extends AbstractRefundCommand {

    @Override
    public void execute(RefundDTO refundDTO) {
        Long accountAmount = this.getAccountAmount(refundDTO.getSellerId());
        if (refundDTO.getAmount() < accountAmount) {
            return;
        }
        if (this.nextCommand != null) {
            this.nextCommand.execute(refundDTO);
        }
        throw new RuntimeException("退款失败");
    }

    private Long getAccountAmount(Long sellerId) {

        return sellerId;
    }
}
