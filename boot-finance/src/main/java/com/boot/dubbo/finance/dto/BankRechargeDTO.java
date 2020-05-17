package com.boot.dubbo.finance.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-05-17 22:52
 */
@Data
public class BankRechargeDTO implements Serializable {
    private static final long serialVersionUID = -6004096563387676163L;

    private String sellerCode;
}
