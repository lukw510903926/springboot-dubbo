package com.boot.dubbo.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-08-17 14:15
 */
@Data
public class BrandIncomeDailyStatistics implements Serializable {

    private static final long serialVersionUID = -1774540024725162262L;
    /**
     * 记录id
     */
    private Long id;
    /**
     * 业务标志码
     */
    private String bizCode;
    /**
     * 逻辑删除标志，0代表正常，1代表已删除
     */
    private Integer deleteMark = 0;
    /**
     * 删除时间戳，当记录状态为正常时，该字段的值为0；如果记录被删除，则该字段代表删除时间，单位为毫秒
     */
    private Long deleteTimestamp = 0L;
    /**
     * 记录创建时间
     */
    private Date gmtCreated;
    /**
     * 记录最后一次修改时间
     */
    private Date gmtModified;

    /**
     * 卖家id
     */
    private Long sellerId;
    /**
     * 订单支付金额
     */
    private Long orderSaleAmount;
    /**
     * 订单支付数量
     */
    private Long orderSaleCount;
    /**
     * 订单退款金额
     */
    private Long orderRefundAmount;
    /**
     * 订单退款数量
     */
    private Long orderRefundCount;
    /**
     * 销售收入
     */
    private Long saleIncome;
    /**
     * 销售退款
     */
    private Long refundIncome;
    /**
     * 发生日期
     */
    private Integer date;
}
