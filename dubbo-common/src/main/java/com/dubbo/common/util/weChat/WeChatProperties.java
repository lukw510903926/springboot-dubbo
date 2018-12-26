package com.dubbo.common.util.weChat;

import lombok.Data;

/**
 * <p>
 * 微信配置
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/26 11:25
 **/
@Data
public class WeChatProperties {

    /**
     * appId
     */
    private String appId;

    /**
     *应用密钥
     */
    private String appSecret;

    /**
     * 商户Id
     */
    private String mchId;

    /**
     * 商户秘钥
     */
    private String mchKey;

    /**
     * 回调地址
     */
    private String notifyUrl;
}
