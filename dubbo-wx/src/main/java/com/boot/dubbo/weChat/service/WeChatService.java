package com.boot.dubbo.weChat.service;

import com.boot.dubbo.weChat.entity.Order;
import com.dubbo.common.util.weChat.WeChatProperties;

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
public interface WeChatService{

    /**
     * 微信支付回调
     * @param request
     * @return
     * @throws Exception
     */
    Order wxPayNotify(String mchKey, HttpServletRequest request) throws Exception ;

    /**
     * 创建订单
     * @param goodsId
     * @return
     */
    Map<String,String> create(String goodsId, HttpServletRequest request) throws Exception ;

    /**
     * 下载微信账单
     * @param weChatProperties
     * @param billDate
     */
    void download(WeChatProperties weChatProperties,String billDate);
}
