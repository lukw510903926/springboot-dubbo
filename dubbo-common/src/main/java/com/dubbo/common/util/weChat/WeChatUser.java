package com.dubbo.common.util.weChat;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信用户信息
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/28 9:52
 **/
@Data
public class WeChatUser implements Serializable {

    private static final long serialVersionUID = -8737303975594338945L;

    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * appId
     */
    private String appId;

    /**
     * openId
     */
    private String openId;

    /**
     * unionid
     */
    private String unionId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像图片地址，完整地址
     */
    private String headImg;

    /**
     * 性别：0未知 1男性 2女性
     */
    private String sex;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 用户前端唯一标识
     */
    private String guid;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

}
