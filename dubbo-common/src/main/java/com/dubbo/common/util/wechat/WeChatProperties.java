package com.dubbo.common.util.wechat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信配置
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/26 11:25
 **/
@Data
@Accessors(chain = true)
public class WeChatProperties {

    /**
     * appId
     */
    private String appId;

    /**
     * 应用密钥
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

    /**
     * 返回地址
     */
    private String returnUrl;

    /**
     * 退款证书地址
     */
    private String certPath;
}
