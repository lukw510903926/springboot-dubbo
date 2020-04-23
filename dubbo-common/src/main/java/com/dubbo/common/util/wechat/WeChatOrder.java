package com.dubbo.common.util.wechat;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : yangqi
 * @project : wdzg-adcenter
 * @createTime : 2019-06-25 18:03
 * @email : lukewei@mockuai.com
 * @description : 微信订单
 */
@Data
@Accessors(chain = true)
public class WeChatOrder implements Serializable {

    private static final long serialVersionUID = -7989543510790476347L;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 订单金额
     */
    private Long totalFee;

    /**
     * 退款金额
     */
    private Long refundFee;

    /**
     * 退款货币种类	默认 CNY
     */
    private String refundFeeType;

    /**
     * 退款原因
     */
    private String refundDesc;

    /**
     * 结果通知url
     */
    private String notifyUrl;

    /**
     * 终端IP
     */
    private String spBillCreateIp;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商品描述
     */
    private String body;
}
