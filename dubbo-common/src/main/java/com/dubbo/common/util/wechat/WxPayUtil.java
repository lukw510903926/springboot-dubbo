package com.dubbo.common.util.wechat;

import com.alibaba.fastjson.JSON;
import com.dubbo.common.util.IdUtil;
import com.dubbo.common.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 微信支付工具类
 * @since : 2020/4/19 11:09 下午
 */
@Slf4j
public class WxPayUtil {

    private WxPayUtil() {
    }


    public static final String SUCCESS = "SUCCESS";

    /**
     * 微信账单下载
     */
    private static final String DOWN_LOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 微信创建订单
     */
    private static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信退款
     */
    private static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 订单查询
     */
    private static final String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 退款查询接口
     */
    private static final String ORDER_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";

    public static final String RETURN_CODE = "return_code";

    public static final String RESULT_CODE = "result_code";

    private static final String SIGN_KEY = "sign";

    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setSocketTimeout(10000)
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setRedirectsEnabled(true)
            .build();

    /**
     * 查询微信订单信息
     * 查询失败 返回空 调用者自己判断异常
     *
     * @param properties 支付账号信息
     * @param orderSn    订单号(平台)
     * @return map
     */
    public static Map<String, String> queryOrder(WeChatProperties properties, String orderSn) {

        Map<String, String> paramMap = new TreeMap<>();
        paramMap.put("appid", properties.getAppId());
        paramMap.put("mch_id", properties.getMchId());
        paramMap.put("nonce_str", getNonceStr());
        paramMap.put("out_trade_no", orderSn);
        String sign = generateSignature(paramMap, properties.getMchKey());
        paramMap.put(SIGN_KEY, sign);

        log.info("queryOrder>>>>>>>>>>>>>>>>>>>>>>>>>paramMap: {}", JSON.toJSONString(paramMap));
        String xmlData = mapToXml(paramMap);
        String response;
        try {
            response = requestXml(ORDER_QUERY, xmlData);
        } catch (Exception e) {
            log.error("微信订单查询失败", e);
            return Collections.emptyMap();
        }
        log.info("queryOrder>>>>>>>>>>>>>>>>>>>>>>>>>response: {}", response);
        Map<String, String> result = xmlToMap(response);
        if (MapUtils.isEmpty(result)) {
            return Collections.emptyMap();
        }
        if (!SUCCESS.equals(result.get(RESULT_CODE))) {
            log.error("order query  error:{}", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 查询微信订单信息
     * 查询失败 返回空 调用者自己判断异常
     *
     * @param properties 支付账号信息
     * @param orderSn    订单号(平台)
     * @return map
     */
    public static Map<String, String> queryRefundOrder(WeChatProperties properties, String orderSn) {

        Map<String, String> paramMap = new TreeMap<>();
        paramMap.put("appid", properties.getAppId());
        paramMap.put("mch_id", properties.getMchId());
        paramMap.put("nonce_str", getNonceStr());
        paramMap.put("out_trade_no", orderSn);
        String sign = generateSignature(paramMap, properties.getMchKey());
        paramMap.put(SIGN_KEY, sign);

        log.info("queryOrder>>>>>>>>>>>>>>>>>>>>>>>>>paramMap: {}", JSON.toJSONString(paramMap));
        String xmlData = mapToXml(paramMap);
        String response;
        try {
            response = requestXml(ORDER_REFUND_QUERY, xmlData);
        } catch (Exception e) {
            log.error("微信退款订单查询失败", e);
            return Collections.emptyMap();
        }
        log.info("queryRefundOrder>>>>>>>>>>>>>>>>>>>>>>>>>response: {}", response);
        Map<String, String> result = xmlToMap(response);
        if (MapUtils.isEmpty(result)) {
            return Collections.emptyMap();
        }
        if (!SUCCESS.equals(result.get(RESULT_CODE))) {
            log.error(" query order refund   error:{}", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 微信统一下单接口
     * <p>
     * 获取H5 prepay_id
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     *
     * @param weChatOrder weChatOrder
     * @param properties  properties
     * @return map
     * @throws ServiceException ServiceException
     */
    public static Map<String, String> getH5PayUrl(com.dubbo.common.util.wechat.WeChatOrder weChatOrder, WeChatProperties properties) {

        Map<String, String> paramMap = new TreeMap<>();
        paramMap.put("attach", org.apache.commons.lang.StringUtils.trim(weChatOrder.getAttach()));
        paramMap.put("appid", properties.getAppId());
        paramMap.put("body", org.apache.commons.lang.StringUtils.trim(weChatOrder.getBody()));
        paramMap.put("mch_id", properties.getMchId());
        paramMap.put("nonce_str", getNonceStr());
        paramMap.put("notify_url", properties.getNotifyUrl());
        paramMap.put("out_trade_no", org.apache.commons.lang.StringUtils.trim(weChatOrder.getOutTradeNo()));
        paramMap.put("spbill_create_ip", org.apache.commons.lang.StringUtils.trim(weChatOrder.getSpBillCreateIp()));
        paramMap.put("total_fee", "" + weChatOrder.getTotalFee());
        paramMap.put("trade_type", "MWEB");
        String sign = generateSignature(paramMap, properties.getMchKey());
        paramMap.put(SIGN_KEY, sign);

        log.info("getH5PayUrl>>>>>>>>>>>>>>>>>>>>>>>>>paramMap: {}", JSON.toJSONString(paramMap));
        String xmlData = mapToXml(paramMap);
        String response;
        try {
            response = requestXml(PAY_URL, xmlData);
        } catch (Exception e) {
            log.error("微信H5下单失败", e);
            throw new ServiceException(e);
        }
        log.info("getH5PayUrl>>>>>>>>>>>>>>>>>>>>>>>>>response: {}", response);
        Map<String, String> result = xmlToMap(response);
        if (MapUtils.isEmpty(result)) {
            throw new ServiceException();
        }
        if (!SUCCESS.equals(result.get(RESULT_CODE))) {
            //直接返回微信支付商户号或密钥配置错误
            log.error("get wx prepay id error:{} paramMap {}", JSON.toJSONString(result), JSON.toJSONString(paramMap));
            throw new ServiceException();
        }
        return result;
    }

    /**
     * 创建订单
     *
     * @param weChatProperties properties
     * @param weChatOrder      chatOrder
     * @return map
     */
    public static Map<String, String> createJsApi(WeChatProperties weChatProperties, com.dubbo.common.util.wechat.WeChatOrder weChatOrder) {

        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", weChatProperties.getAppId());
        data.put("mch_id", weChatProperties.getMchId());
        data.put("nonce_str", getNonceStr());
        data.put("sign_type", "md5");
        data.put("body", "游戏支付");
        data.put("out_trade_no", weChatOrder.getOutTradeNo());
        data.put("total_fee", weChatOrder.getTotalFee() + "");
        data.put("spbill_create_ip", weChatOrder.getSpBillCreateIp());
        data.put("notify_url", weChatProperties.getNotifyUrl());
        data.put("trade_type", "JSAPI");
        data.put("openid", "odU5Cv3t7jW7di0y-P-KWsbM8Kz0");
        //统一下单
        String sign = generateSignature(data, weChatProperties.getMchKey());
        data.put(SIGN_KEY, sign);
        String xml = mapToXml(data);
        String resultXml = requestXml(PAY_URL, xml);
        //返回结果转换
        Map<String, String> resultMap = xmlToMap(resultXml);
        log.info("创建支付请求数据：" + xml);
        log.info("创建支付返回数据：" + resultMap.toString());
        if (SUCCESS.equals(resultMap.get(RETURN_CODE)) && SUCCESS.equals(resultMap.get(RESULT_CODE))) {
            Map<String, String> params = new TreeMap<>();
            params.put("appId", resultMap.get("appid"));
            params.put("timeStamp", getCurrentTimestamp());
            params.put("nonceStr", getNonceStr());
            params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            params.put("signType", "md5");
            String paySign = generateSignature(params, weChatProperties.getMchKey());
            params.put("paySign", paySign);
            params.put("orderNo", weChatOrder.getOutTradeNo());
            return params;
        } else {
            throw new ServiceException("错误接口返回：" + resultMap.toString());
        }
    }

    /**
     * 微信验签
     *
     * @param paramMap   paramMap
     * @param properties properties
     * @return boolean
     */
    public static boolean signVerified(Map<String, String> paramMap, WeChatProperties properties) {

        String sign = paramMap.get(SIGN_KEY);
        if (MapUtils.isNotEmpty(paramMap)) {
            Map<String, String> signMap = new TreeMap<>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                if (!SIGN_KEY.equals(entry.getKey())) {
                    signMap.put(entry.getKey(), entry.getValue());
                }
            }
            String partnerKey = properties.getMchKey();
            String actualSign = generateSignature(signMap, partnerKey);
            log.info("weChat pay sign check paramMap : {} sign: {} actualSign :{}", JSON.toJSONString(paramMap), sign, actualSign);
            return sign.equals(actualSign);
        }
        return false;
    }

    /**
     * 账单下载
     *
     * @param billDate         日期
     * @param weChatProperties 微信配置
     */
    public static String download(String billDate, WeChatProperties weChatProperties) {

        Map<String, String> data = new TreeMap<>();
        data.put("appid", weChatProperties.getAppId());
        data.put("mch_id", weChatProperties.getMchId());
        data.put("nonce_str", getNonceStr());
        data.put("sign_type", "MD5");
        data.put("bill_date", billDate);
        //ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单 REFUND，返回当日退款订单，RECHARGE_REFUND，返回当日充值退款订单
        data.put("bill_type", "ALL");
        try {
            String sign = generateSignature(data, weChatProperties.getMchKey());
            data.put(SIGN_KEY, sign);
            String xml = mapToXml(data);
            log.info("request xml : {}", xml);
            String results = requestXml(DOWN_LOAD_BILL_URL, xml);
            log.info("result : {}", results);
            return results;
        } catch (Exception ex) {
            log.error("下载微信账单失败", ex);
            throw new ServiceException("下载微信账单失败");
        }
    }

    /**
     * 退款
     *
     * @param weChatProperties weChatProperties
     * @param weChatOrder      weChatOrder
     * @return String
     */
    public static Map<String, String> refund(WeChatProperties weChatProperties, com.dubbo.common.util.wechat.WeChatOrder weChatOrder) {

        Map<String, String> data = new TreeMap<>();
        data.put("appid", weChatProperties.getAppId());
        data.put("mch_id", weChatProperties.getMchId());
        data.put("nonce_str", getNonceStr());
        data.put("out_trade_no", weChatOrder.getOutTradeNo());
        data.put("out_refund_no", weChatOrder.getOutRefundNo());
        data.put("total_fee", weChatOrder.getTotalFee() + "");
        data.put("refund_fee", weChatOrder.getRefundFee() + "");
        String sign = generateSignature(data, weChatProperties.getMchKey());
        data.put(SIGN_KEY, sign);
        String xml = mapToXml(data);
        log.info("refund xml : {}", xml);
        SSLConnectionSocketFactory socketFactory = initCert(weChatProperties);
        String resultXml = requestSslXml(REFUND_URL, xml, socketFactory);
        log.info("resultXml : {}", resultXml);
        return xmlToMap(resultXml);
    }

    /**
     * 初始化证书
     *
     * @param properties properties
     * @return SSLConnectionSocketFactory
     */
    private static SSLConnectionSocketFactory initCert(WeChatProperties properties) {

        File file = new File(properties.getCertPath());
        log.info("证书路径 : {}", properties.getCertPath());
        try (FileInputStream inputStream = new FileInputStream(file)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, properties.getMchId().toCharArray());
            SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, properties.getMchId().toCharArray()).build();
            return new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (Exception e) {
            log.error("微信证书初始化错误", e);
            throw new ServiceException("微信证书初始化错误", e);
        }

    }

    /**
     * 提交xml方式请求
     *
     * @param url 请求地址
     * @param xml 请求xml
     * @return string
     * @throws ServiceException ServiceException
     */
    private static String requestXml(String url, String xml) {

        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(xml, StandardCharsets.UTF_8);
        stringEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(stringEntity);
        String responseContent;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            httpPost.setConfig(REQUEST_CONFIG);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("send http requestBase : {} error : ", httpPost, e);
            throw new ServiceException("微信请求失败 : ", e);
        }
        return responseContent;
    }

    /**
     * 提交xml方式请求
     *
     * @param url 请求地址
     * @param xml 请求xml
     * @return String
     */
    private static String requestSslXml(String url, String xml, SSLConnectionSocketFactory socketFactory) {

        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(xml, StandardCharsets.UTF_8);
        stringEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(stringEntity);
        String responseContent;
        try (CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            httpPost.setConfig(REQUEST_CONFIG);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("send http requestBase : {} error : ", httpPost, e);
            throw new ServiceException("微信请求失败 : ", e);
        }
        return responseContent;
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data   待签名数据
     * @param mchKey API密钥
     * @return 签名
     */
    private static String generateSignature(Map<String, String> data, String mchKey) {

        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            signSb.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
        }
        String signStr = signSb.append("key=").append(mchKey).toString();
        log.info("signSb: {}", signStr);
        return DigestUtils.md5Hex(signStr).toUpperCase();
    }

    /**
     * 生成10位时间戳
     *
     * @return
     */
    public static String getCurrentTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 生成nonceStr
     *
     * @return
     */
    public static String getNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data     待签名数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, String signType) {

        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (StringUtils.isNotBlank(data.get(k).trim())) {
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        if ("md5".equalsIgnoreCase(signType)) {
            return md5(sb.toString());
        } else if ("hmacSha256".equalsIgnoreCase(signType)) {
            return hmacSha256(sb.toString(), key);
        } else {
            throw new ServiceException(String.format("Invalid signType: %s", signType));
        }
    }

    /**
     * 生成 md5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String md5(String data) {

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            log.error("加密失败 :", e);
        }
        return null;

    }

    /**
     * 生成 hmacSha256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSha256(String data, String key) {

        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSHA256.init(secretKey);
            byte[] array = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            log.error("加密失败 : ", e);
        }
        return null;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     */
    public static String mapToXml(Map<String, String> data) {

        String output = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = Optional.ofNullable(data.get(key)).orElse("").trim();
                Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            output = writer.getBuffer().toString();
            writer.close();
        } catch (Exception ex) {
            log.error("加密失败 : ", ex);
        }
        return output;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     */
    public static Map<String, String> xmlToMap(String strXML) {
        try {
            Map<String, String> data = new HashMap<>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            stream.close();
            return data;
        } catch (Exception ex) {
            throw new ServiceException("XML格式字符串转换为Map失败");
        }
    }

    /**
     * 提交xml方式请求
     *
     * @param urlStr
     * @param xmlStr
     * @return
     */
    public static String requestXML(String urlStr, String xmlStr) {

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlStr.getBytes(StandardCharsets.UTF_8)));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
            return buffer.toString();
        } catch (IOException e) {
            throw new ServiceException("请求失败 : {}", e);
        }
    }

    public static String transfer(WeChatProperties properties) throws Exception {

        String transfer = "https://api.mch.weixin.qq.com/v3/transfer/batches";
        //申请商户号的appid或商户号绑定的appid（企业号corpid即为此appid）
        String appId = "";
        //用户在直连商户应用下的用户标示
        String openId = "";
        Map<String, Object> postMap = new HashMap<>();

        //商家批次单号 长度 1~32
        String outNo = IdUtil.generate("T");
        postMap.put("appid", appId);
        postMap.put("out_batch_no", outNo);
        //该笔批量转账的名称
        postMap.put("batch_name", "测试转账");
        //转账说明，UTF8编码，最多允许32个字符
        postMap.put("batch_remark", "测试转账");
        //转账金额单位为“分”。 总金额
        postMap.put("total_amount", 100);
        //。转账总笔数
        postMap.put("total_num", 1);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> subMap = new HashMap<>(4);
        //商家明细单号
        subMap.put("out_detail_no", outNo);
        //转账金额
        subMap.put("transfer_amount", 100);
        //转账备注
        subMap.put("transfer_remark", "明细备注1");
        //用户在直连商户应用下的用户标示
        subMap.put("openid", openId);
        list.add(subMap);
        postMap.put("transfer_detail_list", list);
        SSLConnectionSocketFactory socketFactory = initCert(properties);
        String resultXml = requestSslXml(transfer, JSON.toJSONString(postMap), socketFactory);
    }

    /**
     * 取ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
            ip = ip.split(",")[0].trim();
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {

        WeChatProperties properties = new WeChatProperties();
        properties.setCertPath("/Users/yangqi/Downloads/cert/apiclient_cert.p12");
        properties.setAppId("wx06a57803db0fa5ca")
                .setAppSecret("32a7ceb71d3f9fda23b051e0a4a95828")
                .setMchKey("554025ebae4d4ffca8904fb1681d5170")
                .setMchId("1534364321");

        Map<String, String> queryOrder = queryRefundOrder(properties, "T1911132321075445LK38152");
    }
}
