package com.boot.dubbo.weChat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.dubbo.weChat.entity.Order;
import com.boot.dubbo.weChat.entity.Product;
import com.boot.dubbo.weChat.service.IOrderService;
import com.boot.dubbo.weChat.service.IProductService;
import com.boot.dubbo.weChat.service.WeChatService;
import com.dubbo.common.util.IdUtil;
import com.dubbo.common.util.exception.ServiceException;
import com.dubbo.common.util.weChat.WeChatProperties;
import com.dubbo.common.util.weChat.WxPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    private static final String SUCCESS = "SUCCESS";

    /**
     * 微信支付回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    public String wxpayNotify(String mchKey, HttpServletRequest request) throws Exception {

        //接收xml
        ServletInputStream in = request.getInputStream();
        int contentLength = request.getContentLength();
        if (contentLength <= 0) {
            return WxPayUtil.notifyReturnFail("无xml数据");
        }
        byte[] returnData = new byte[contentLength];
        in.read(returnData);
        String xmlStr = new String(returnData, StandardCharsets.UTF_8);
        //判断数据
        if (StringUtils.isBlank(xmlStr)) {
            return WxPayUtil.notifyReturnFail("xml数据为空");
        }
        //xml转map
        Map<String, String> map = WxPayUtil.xmlToMap(xmlStr);
        //判断订单状态
        if (!SUCCESS.equals(map.get("return_code")) || !SUCCESS.equals(map.get("result_code"))) {
            return WxPayUtil.notifyReturnFail("支付返回数据异常：" + map.toString());
        }
        //校验sign
        String signOrigin = map.get("sign");
        String sign = WxPayUtil.generateSignature(map, mchKey, "md5");
        if (!sign.equals(signOrigin)) {
            return WxPayUtil.notifyReturnFail("sign校验失败");
        }
        //校验订单
        String outTradeNo = map.get("out_trade_no");
        Order entity = new Order();
        entity.setOrderNo(outTradeNo);
        Order khOrder = this.orderService.getOne(new QueryWrapper<>(entity));
        if (null == khOrder) {
            return WxPayUtil.notifyReturnFail("订单不存在");
        }
        //校验金额
        if (khOrder.getPrice() != Integer.parseInt(map.get("total_fee"))) {
            return WxPayUtil.notifyReturnFail("用户实际支付金额和订单金额不相符");
        }
        //判断是否已更新状态
        if (khOrder.getIsPay() == 1) {
            return WxPayUtil.notifyReturnFail("订单支付状态已更新，无需重复操作");
        }
        //更新订单状态
        khOrder.setIsPay(1);
        khOrder.setPayAt(new Date());
        khOrder.setUpdatedAt(new Date());
        khOrder.setTransactionId(map.get("transaction_id"));

        this.orderService.updateById(khOrder);
        return WxPayUtil.notifyReturnSuccess();
    }

    /**
     * 创建订单
     *
     * @param goodsId
     * @return
     */
    public Map<String, String> create(String goodsId, HttpServletRequest request) {

        //通过goodsId取游戏价格
        Product entity = new Product();
        entity.setGoodsId(goodsId);
        Product goods = productService.getOne(new QueryWrapper<>(entity));
        if (null == goods) {
            throw new ServiceException("商品不存在");
        }
        //取订单号
        String orderNo = IdUtil.uuid();
        //参数
        String appId = "wx748220b52a13e1b9";
        String mchId = "1447152002";
        String mchKey = "yueting123ytydt23edf432weerrfde4";
        String notifyUrl = "http://m.jordonyu.com/rouge/api/order/notify";
        //通过goodsId取游戏价格
        if (goods.getGamePrice() == 0) {
            throw new ServiceException("商品价格未配置，请联系管理员");
        }
        //组装
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", appId);
        data.put("mch_id", mchId);
        data.put("nonce_str", WxPayUtil.getNonceStr());
        data.put("sign_type", "md5");
        data.put("body", "游戏支付");
        data.put("out_trade_no", orderNo);
        data.put("total_fee", goods.getGamePrice().toString());
        data.put("spbill_create_ip", WxPayUtil.getIp(request));
        data.put("notify_url", notifyUrl);
        data.put("trade_type", "JSAPI");
        data.put("openid", "odU5Cv3t7jW7di0y-P-KWsbM8Kz0");
        //统一下单
        String sign = WxPayUtil.generateSignature(data, mchKey, "md5");
        data.put("sign", sign);
        String xml = WxPayUtil.mapToXml(data);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String resultXml = WxPayUtil.requestXML(url, xml);
        //返回结果转换
        Map<String, String> resultMap = WxPayUtil.xmlToMap(resultXml);
        log.info("创建支付请求数据：" + xml);
        log.info("创建支付返回数据：" + resultMap.toString());
        if (SUCCESS.equals(resultMap.get("return_code")) && SUCCESS.equals(resultMap.get("result_code"))) {
            Map<String, String> params = new TreeMap<>();
            params.put("appId", resultMap.get("appid"));
            params.put("timeStamp", WxPayUtil.getCurrentTimestamp());
            params.put("nonceStr", WxPayUtil.getNonceStr());
            params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            params.put("signType", "md5");
            String paySign = WxPayUtil.generateSignature(params, mchKey, "md5");
            params.put("paySign", paySign);
            params.put("orderNo", orderNo);
            //生成订单记录
            Order khOrder = new Order();
            khOrder.setUserId("2");
            khOrder.setNickname("yangqi");
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
    public void download(WeChatProperties weChatProperties, String billDate) {

        Map<String, String> data = new TreeMap<>();
        data.put("appid", weChatProperties.getAppId());
        data.put("mch_id", weChatProperties.getMchId());
        data.put("nonce_str", WxPayUtil.getNonceStr());
        data.put("sign_type", "MD5");
        data.put("bill_date", billDate);
        //ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单 REFUND，返回当日退款订单，RECHARGE_REFUND，返回当日充值退款订单
        data.put("bill_type", SUCCESS);
        try {
            String sign = WxPayUtil.generateSignature(data, weChatProperties.getMchKey(), "MD5");
            data.put("sign", sign);
            String xml = WxPayUtil.mapToXml(data);
            String url = "https://api.mch.weixin.qq.com/pay/downloadbill";
            List<String> results = WxPayUtil.sendRequestXml(url, xml);
            log.info("result : {}", results);
        } catch (Exception ex) {
            log.error("下载微信账单失败", ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
