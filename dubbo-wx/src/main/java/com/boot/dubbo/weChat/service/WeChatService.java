package com.boot.dubbo.weChat.service;

import com.boot.dubbo.weChat.entity.Order;
import com.dubbo.common.util.weChat.WeChatUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 10:58
 **/
public interface WeChatService {

    /**
     * 微信支付回调
     *
     * @param request
     * @return
     */
    Order wxPayNotify(HttpServletRequest request);

    /**
     * 创建订单
     *
     * @param goodsId
     * @return
     */
    Map<String, String> create(String goodsId, WeChatUser weChatUser, HttpServletRequest request);

    /**
     * 微信订单退款
     *
     * @param orderNo
     */
    void refund(String orderNo);

    /**
     * 下载微信账单
     *
     * @param billDate
     */
    void download(String billDate);
}
