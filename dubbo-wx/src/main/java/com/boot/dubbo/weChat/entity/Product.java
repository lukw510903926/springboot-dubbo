package com.boot.dubbo.weChat.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 **/
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = -5141180397587271288L;

    /**
     * 主键
     */
    private Long id;

    /**
     * uuid
     */
    private String goodsId;

    /**
     * 商品品牌
     */
    private String brand;

    /**
     * rgb
     */
    private String rgb;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 专柜原价
     */
    private Integer originPrice;

    /**
     * 商品特征，口红色号等
     */
    private String goodsColorNo;

    /**
     * 游戏价格
     */
    private Integer gamePrice;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 难度系数
     */
    private Integer rate;

    /**
     * 图片
     */
    private String goodsPic;

    /**
     *封面 图片
     */
    private String coverPic;

    /**
     * 状态，1上架 0下架
     */
    private Integer status;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 是否已删除，0未删除 1已删除
     */
    private Integer isDeleted;
}
