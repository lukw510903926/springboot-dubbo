package com.boot.dubbo.weChat.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 11:13
 **/
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -732731383522669887L;

    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 交易流水号
     */
    private String transactionId;

    /**
     * 商品原价，单位分
     */
    private Integer originPrice;

    /**
     * 订单金额，单位分
     */
    private Integer price;

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 商品名称（冗余）
     */
    private String goodsName;
    /**
     * 是否支付，0未支付 1已支付
     */
    private Integer isPay;
    /**
     * 支付时间
     */
    private Date payAt;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 是否已删除，0未删除 1已删除
     */
    private Integer isDeleted;

}
