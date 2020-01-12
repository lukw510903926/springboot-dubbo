package com.boot.dubbo.finance.constants;

import java.util.Optional;

public enum RoleEnum {

    UN_KNOW(0, "未知"),

    SELLER(1, "卖家"),

    PLATFORM(2, "平台"),

    DISTRIBUTION(3, "分佣人");

    private int code;

    private String desc;

    RoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public RoleEnum getRoleEnum(Integer code) {

        code = Optional.ofNullable(code).orElse(0);
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.code == code) {
                return roleEnum;
            }
        }
        return UN_KNOW;
    }
}
