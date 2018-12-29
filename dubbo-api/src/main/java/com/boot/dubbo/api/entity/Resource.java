package com.boot.dubbo.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/29 17:36
 **/
@Data
public class Resource implements Serializable {

    private static final long serialVersionUID = -5569555777471536551L;

    private Integer id;

    private String name;

    private LocalDateTime created;
}
