package com.boot.dubbo.finance.constants;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-01-12 18:12
 */
public enum OrderHandlerEnum {

    /**
     * 普通订单
     */
    NORMAL_ORDER(1, "普通订单"),

    /**
     * 品牌商订单
     */
    BRAND_ORDER(2, "品牌商订单"),

    /**
     * 推广订单
     */
    PROMOTER_ORDER(3, "推广订单");

    private int code;

    private String desc;

    OrderHandlerEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
