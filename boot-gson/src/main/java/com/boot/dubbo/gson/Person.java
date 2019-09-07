package com.boot.dubbo.gson;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class Person implements Serializable {

    private static final long serialVersionUID = -6631720063938151568L;

    private Long id;

    private String userName;

    private Date birthday;
}
