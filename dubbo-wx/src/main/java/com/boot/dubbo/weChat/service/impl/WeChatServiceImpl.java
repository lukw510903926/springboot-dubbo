package com.boot.dubbo.weChat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.dubbo.weChat.entity.Order;
import com.boot.dubbo.weChat.entity.Product;
import com.boot.dubbo.weChat.service.IOrderService;
import com.boot.dubbo.weChat.service.IProductService;
import com.boot.dubbo.weChat.service.WeChatService;
import com.dubbo.common.util.IdUtil;
import com.dubbo.common.util.exception.ServiceException;
import com.dubbo.common.util.weChat.WeChatOrder;
import com.dubbo.common.util.weChat.WeChatProperties;
import com.dubbo.common.util.weChat.WeChatUser;
import com.dubbo.common.util.weChat.WxPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 10:58
 **/
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private WeChatProperties weChatProperties;

    private static final String SUCCESS = "SUCCESS";

    /**
     * 微信创建订单
     */
    private static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private static final String RETURN_CODE = "return_code";

    private static final String RESULT_CODE = "result_code";

    /**
     * 微信支付回调
     *
     * @param request
     * @return
     */
    @Override
    public Order wxPayNotify(HttpServletRequest request) {

        try {
            String xml = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.toString());
            //判断数据
            if (StringUtils.isBlank(xml)) {
                throw new ServiceException("订单数据为空");
            }
            //xml转map
            Map<String, String> map = WxPayUtil.xmlToMap(xml);
            //判断订单状态
            if (!SUCCESS.equals(map.get(RETURN_CODE)) || !SUCCESS.equals(map.get(RESULT_CODE))) {
                throw new ServiceException("支付返回数据异常：" + map.toString());
            }
            //校验sign
            String signOrigin = map.get("sign");
            String sign = WxPayUtil.generateSignature(map, weChatProperties.getMchKey(), "md5");
            if (!sign.equals(signOrigin)) {
                throw new ServiceException("认证签名校验失败");
            }
            //校验订单
            String outTradeNo = map.get("out_trade_no");
            Order entity = new Order();
            entity.setOrderNo(outTradeNo);
            Order order = this.orderService.getOne(new QueryWrapper<>(entity));
            if (null == order) {
                throw new ServiceException("订单不存在");
            }
            //校验金额
            if (!(order.getPrice() + "").equals(map.get("total_fee"))) {
                throw new ServiceException("用户实际支付金额和订单金额不相符");
            }
            //判断是否已更新状态
            if (order.getIsPay() != null && order.getIsPay() == 1) {
                throw new ServiceException("订单支付已支付，无需重复操作");
            }
            //更新订单状态
            order.setIsPay(1);
            order.setPayAt(new Date());
            order.setUpdatedAt(new Date());
            order.setTransactionId(map.get("transaction_id"));
            this.orderService.updateById(order);
            return order;
        } catch (Exception e) {
            log.error("支付回调失败 : ", e);
            throw new ServiceException("支付回调失败 ");
        }
    }


    @Override
    public void refund(String orderNo) {

        if (StringUtils.isEmpty(orderNo)) {
            throw new ServiceException("订单不存在");
        }
        WeChatOrder weChatOrder = new WeChatOrder();
        weChatOrder.setOutTradeNo(orderNo);
        Map<String, String> resultMap = WxPayUtil.refund(weChatProperties, weChatOrder);
        //返回结果转换
        log.info("创建支付返回数据 resultMap ：{}", resultMap.toString());
        if (SUCCESS.equals(resultMap.get(RETURN_CODE)) && SUCCESS.equals(resultMap.get(RESULT_CODE))) {
            log.info("resultMap : {}", resultMap);
        } else {
            throw new ServiceException("错误接口返回：" + resultMap.toString());
        }
    }

    /**
     * 创建订单
     *
     * @param goodsId
     * @return
     */
    @Override
    public Map<String, String> create(String goodsId, WeChatUser weChatUser, HttpServletRequest request) {

        //通过goodsId取游戏价格
        Product entity = new Product();
        entity.setGoodsId(goodsId);
        Product goods = productService.getOne(new QueryWrapper<>(entity));
        if (null == goods) {
            throw new ServiceException("商品不存在");
        }
        //取订单号
        String orderNo = IdUtil.uuid();
        //通过goodsId取游戏价格
        if (goods.getGamePrice() <= 0) {
            throw new ServiceException("商品价格未配置，请联系管理员");
        }
        //组装
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", weChatProperties.getAppId());
        data.put("mch_id", weChatProperties.getMchId());
        data.put("nonce_str", WxPayUtil.getNonceStr());
        data.put("sign_type", "md5");
        data.put("body", "游戏支付");
        data.put("out_trade_no", orderNo);
        data.put("total_fee", goods.getGamePrice().toString());
        data.put("spbill_create_ip", WxPayUtil.getIp(request));
        data.put("notify_url", weChatProperties.getNotifyUrl());
        data.put("trade_type", "JSAPI");
        data.put("openid", "odU5Cv3t7jW7di0y-P-KWsbM8Kz0");
        //统一下单
        String sign = WxPayUtil.generateSignature(data, weChatProperties.getMchKey(), "md5");
        data.put("sign", sign);
        String xml = WxPayUtil.mapToXml(data);
        String resultXml = WxPayUtil.requestXML(PAY_URL, xml);
        //返回结果转换
        Map<String, String> resultMap = WxPayUtil.xmlToMap(resultXml);
        log.info("创建支付请求数据：" + xml);
        log.info("创建支付返回数据：" + resultMap.toString());
        if (SUCCESS.equals(resultMap.get(RETURN_CODE)) && SUCCESS.equals(resultMap.get(RESULT_CODE))) {
            Map<String, String> params = new TreeMap<>();
            params.put("appId", resultMap.get("appid"));
            params.put("timeStamp", WxPayUtil.getCurrentTimestamp());
            params.put("nonceStr", WxPayUtil.getNonceStr());
            params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            params.put("signType", "md5");
            String paySign = WxPayUtil.generateSignature(params, weChatProperties.getMchKey(), "md5");
            params.put("paySign", paySign);
            params.put("orderNo", orderNo);
            //生成订单记录
            Order khOrder = new Order();
            khOrder.setUserId(weChatUser.getUserId());
            khOrder.setOrderNo(orderNo);
            khOrder.setOriginPrice(goods.getOriginPrice());
            khOrder.setPrice(goods.getGamePrice());
            khOrder.setGoodsId(goodsId);
            khOrder.setGoodsName(goods.getGoodsName());
            khOrder.setIsPay(0);
            khOrder.setCreatedAt(new Date());
            khOrder.setUpdatedAt(new Date());
            orderService.save(khOrder);
            return params;
        } else {
            throw new ServiceException("错误接口返回：" + resultMap.toString());
        }
    }

    @Override
    public void download(String billDate) {

        try {
            String results = WxPayUtil.download(billDate, weChatProperties);
            log.info("result : {}", results);
        } catch (Exception ex) {
            log.error("下载微信账单失败", ex);
            throw new ServiceException("下载微信账单失败");
        }
    }
}
