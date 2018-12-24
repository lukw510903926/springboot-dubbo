package com.dubbo.common.util.weChat;

import com.dubbo.common.util.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.*;

/**
 * 微信支付工具类
 */
public class WxPayUtil {

    private static Logger logger = LoggerFactory.getLogger(WxPayUtil.class);

    private static final String CHART_SET = "UTF-8";

    private WxPayUtil() {
    }

    /**
     * 生成10位时间戳
     *
     * @return
     */
    public static String getCurrentTimestamp() {
        long l = System.currentTimeMillis() / 1000;
        return String.valueOf(l);
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
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
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
        if ("md5".equals(signType)) {
            return md5(sb.toString());
        } else if ("hmacSha256".equals(signType)) {
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
            byte[] array = md.digest(data.getBytes(CHART_SET));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            logger.error("加密失败 : {}", e);
        }
        return null;

    }

    /**
     * 生成 hmacSha256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String hmacSha256(String data, String key) {

        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHART_SET), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] array = sha256HMAC.doFinal(data.getBytes(CHART_SET));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            logger.error("加密失败 : {}", e);
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
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, CHART_SET);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            output = writer.getBuffer().toString();
            writer.close();
        } catch (Exception ex) {
            logger.error("加密失败 : {}", ex);
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
            InputStream stream = new ByteArrayInputStream(strXML.getBytes(CHART_SET));
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
     * @throws Exception
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
            out.write(new String(xmlStr.getBytes(CHART_SET)));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new ServiceException("请求失败 : {}", e);
        }
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

    public static String notifyReturnSuccess() {

        Map<String, String> map = new HashMap<>();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");
        return mapToXml(map);
    }

    public static String notifyReturnFail(String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("return_code", "FAIL");
        map.put("return_msg", "notify失败：" + msg);
        return mapToXml(map);
    }
}
