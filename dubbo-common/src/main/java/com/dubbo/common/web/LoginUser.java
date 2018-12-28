package com.dubbo.common.web;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/28 10:04
 **/
@Data
@ToString
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 3187048948046044742L;

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实名称
     */
    private String name;

    /**
     * 注册时间
     */
    private Date created;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 权限
     */
    private List<String> permissions = new ArrayList<>(0);

    /**
     * 角色
     */
    private List<String> roles = new ArrayList<>(0);
}
