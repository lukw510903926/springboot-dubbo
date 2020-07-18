package com.boot.dubbo.finance.refund.command;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 退款基础类
 * @since : 2020-07-18 23:37
 */
@Data
public class RefundDTO implements Serializable {
    private static final long serialVersionUID = -6329193341761202555L;

    private Long sellerId;

    private Long amount;
}
