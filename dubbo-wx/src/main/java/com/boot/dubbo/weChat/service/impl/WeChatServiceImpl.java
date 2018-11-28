package com.boot.dubbo.weChat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.dubbo.weChat.dao.OrderMapper;
import com.boot.dubbo.weChat.dao.ProductMapper;
import com.boot.dubbo.weChat.entity.Order;
import com.boot.dubbo.weChat.entity.Product;
import com.boot.dubbo.weChat.service.IOrderService;
import com.boot.dubbo.weChat.service.IProductService;
import com.boot.dubbo.weChat.service.WeChatService;
import com.dubbo.common.util.IdUtil;
import com.dubbo.common.util.exception.ServiceException;
import com.dubbo.common.util.weChat.WxpayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
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
            return WxpayUtil.notifyReturnFail("无xml数据");
        }
        byte[] returnData = new byte[contentLength];
        in.read(returnData);
        String xmlStr = new String(returnData, "UTF-8");
        //判断数据
        if (StringUtils.isBlank(xmlStr)) {
            return WxpayUtil.notifyReturnFail("xml数据为空");
        }
        //xml转map
        Map<String, String> map = WxpayUtil.xmlToMap(xmlStr);
        //判断订单状态
        if (!"SUCCESS".equals(map.get("return_code")) || !"SUCCESS".equals(map.get("result_code"))) {
            return WxpayUtil.notifyReturnFail("支付返回数据异常：" + map.toString());
        }
        //校验sign
        String signOrigin = map.get("sign");
        String sign = WxpayUtil.generateSignature(map, mchKey, "MD5");
        if (!sign.equals(signOrigin)) {
            return WxpayUtil.notifyReturnFail("sign校验失败");
        }
        //校验订单
        String outTradeNo = map.get("out_trade_no");
        Order entity = new Order();
        entity.setOrderNo(outTradeNo);
        Order khOrder = this.orderService.getOne(new QueryWrapper<>(entity));
        if (null == khOrder) {
            return WxpayUtil.notifyReturnFail("订单不存在");
        }
        //校验金额
        if (khOrder.getPrice() != Integer.parseInt(map.get("total_fee"))) {
            return WxpayUtil.notifyReturnFail("用户实际支付金额和订单金额不相符");
        }
        //判断是否已更新状态
        if (khOrder.getIsPay() == 1) {
            return WxpayUtil.notifyReturnFail("订单支付状态已更新，无需重复操作");
        }
        //更新订单状态
        khOrder.setIsPay(1);
        khOrder.setPayAt(new Date());
        khOrder.setUpdatedAt(new Date());
        khOrder.setTransactionId(map.get("transaction_id"));

        boolean n = this.orderService.updateById(khOrder);
        if (n) {
            return WxpayUtil.notifyReturnSuccess();
        }
        return WxpayUtil.notifyReturnFail("更新订单状态失败");
    }

    /**
     * 创建订单
     *
     * @param goodsId
     * @return
     */
    public Map<String, String> create(String goodsId, HttpServletRequest request) throws Exception {

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
        data.put("nonce_str", WxpayUtil.getNonceStr());
        data.put("sign_type", "MD5");
        data.put("body", "游戏支付");
        data.put("out_trade_no", orderNo);
        data.put("total_fee", goods.getGamePrice().toString());
        data.put("spbill_create_ip", WxpayUtil.getIp(request));
        data.put("notify_url", notifyUrl);
        data.put("trade_type", "JSAPI");
        data.put("openid", "odU5Cv3t7jW7di0y-P-KWsbM8Kz0");
        //统一下单
        String sign = WxpayUtil.generateSignature(data, mchKey, "MD5");
        data.put("sign", sign);
        String xml = WxpayUtil.mapToXml(data);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String resultXml = WxpayUtil.requestXML(url, xml);
        //返回结果转换
        Map<String, String> resultMap = WxpayUtil.xmlToMap(resultXml);
        log.info("创建支付请求数据：" + xml);
        log.info("创建支付返回数据：" + resultMap.toString());
        if ("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))) {
            Map<String, String> params = new TreeMap<>();
            params.put("appId", resultMap.get("appid"));
            params.put("timeStamp", WxpayUtil.getCurrentTimestamp());
            params.put("nonceStr", WxpayUtil.getNonceStr());
            params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            params.put("signType", "MD5");
            String paySign = WxpayUtil.generateSignature(params, mchKey, "MD5");
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
            boolean n = orderService.save(khOrder);
            if (n) {
                return params;
            }
            throw new ServiceException("创建订单失败");
        } else {
            throw new ServiceException("错误接口返回：" + resultMap.toString());
        }
    }
}
