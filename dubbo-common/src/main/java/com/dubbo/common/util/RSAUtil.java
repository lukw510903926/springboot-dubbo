package com.dubbo.common.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/27 18:42
 **/

public class RSAUtil {

    /**
     * 定义加密方式
     */
    private static final String KEY_RSA = "RSA";
    /**
     * 定义签名算法
     */
    private static final String KEY_RSA_SIGNATURE = "MD5withRSA";
    /**
     * 定义公钥算法
     */
    private static final String RSA_PUBLIC_KEY = "RSAPublicKey";
    /**
     * 定义私钥算法
     */
    private static final String RSA_PRIVATE_KEY = "RSAPrivateKey";


    /**
     * 公钥
     */
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtax1YBU7TsDGIKGZ5/y2t1+daAeObie1IXXl6ZFT8vd+cNaU4S/bITftjSlpJhFGwdISm70eoQ0AVQ6KG8QikAS+UKLo/16A2KaV7y1DzzRJv30u6UDHGsE+4OyCZ/0s35BA3ymLfds8cH1TBGm5H63IjrxL/Yo71/fRFdr6ILwOqRzxk7MZVZheqrbW305Xm2qlCwXPaOjYGyd7TNGm7EVXmai1Tid4yRa0iG+peUM3elHqmnJQZVefMsncR1dJPAWOLn0qor4AgjWP9ZCOTewSHtLAn/D6MbRBqswuhBpkGluhzpp/Gy9025ZUHphqA0aFwX2x/1X8xzSi8DMtqQIDAQAB";

    /**
     * 私钥
     */
    public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1rHVgFTtOwMYgoZnn/La3X51oB45uJ7UhdeXpkVPy935w1pThL9shN+2NKWkmEUbB0hKbvR6hDQBVDoobxCKQBL5Qouj/XoDYppXvLUPPNEm/fS7pQMcawT7g7IJn/SzfkEDfKYt92zxwfVMEabkfrciOvEv9ijvX99EV2vogvA6pHPGTsxlVmF6qttbfTlebaqULBc9o6NgbJ3tM0absRVeZqLVOJ3jJFrSIb6l5Qzd6UeqaclBlV58yydxHV0k8BY4ufSqivgCCNY/1kI5N7BIe0sCf8PoxtEGqzC6EGmQaW6HOmn8bL3TbllQemGoDRoXBfbH/VfzHNKLwMy2pAgMBAAECggEAKJr7QRZ9wwRjRDfaZnedfy7nx7CorQZC/c+UK7hHwy5+HEpZzFsd5zbQ6ZQv5EpIPlntvtFuXyDAykINrFcSQo5M20kOOsWslr/lEyhS16UgNqSML6ZC2kAHBc7W9tcniHB9j2OBwgeWYq4gOARJVENLaFu9wFV9OrAxE2l2Lop4/NeFEvRPZSnAB91o0pBW3+kSg0cjuMo9NkvJYAF9/ZyjBjCu1JpmDkoUfppEdA579iaKFTzoZt16j0IxOS66UucKYRKW8997FoGSZB8jJcMBNVu9LKLZN8ryIpBfhzeqNVOqlJaRA66VsX5kzBmvQSMP0DxVjR4mPW6LOw/gAQKBgQDuA+xrTcG9PjNyq6XIY9mknUh6st+OGdHD4OTUE0sG6tZauTAnA6nlcUk7EUV4I8/aI7uZxcmiacH2TcAwvE37VmrnmSQXXJ+uREk5GrAwA783KKwUrGjzIpUV5ZrHKj5bUlCk63BqW2zl0WUyv+APKbzugWnaFvqTyHPXTr5GgQKBgQDDZq8F4WRixtdWyUhpOOJJ2RVmY3INTJvwAZ48wn5pNsv0BOXNiNODz8LMyGsnKBl2tBRIy5avUKD1tbKOrrCZyZ4Nw63xL6K+WoDyOp9eEHbVyDSb3bhND1QgfSUQQPyWC+PXaaVMoq+FHJNzHicHeJsNDl72XJ4uxRDlv3ZjKQKBgGQUqEOSZYWScwWjoUj6m1h5fjGBpzjUKHzpwq5eEm0tL8bkGZg+FSFSGk3I/GIBBPQflFGbdiZhu9o6ZTbY5x1o//i0TxW3r7kSI4vsaes2mB+0bW8f891QTEnxyrw2ShQSL7C5AwLlwIt+GcRWI/ucPEI5jm3SvbiIUBVXmfCBAoGBAJqicHAe5uqkoVW1BPEAa1Q1z5GvsVqE/gL5xNggsxlgnBcIqTP2zCzMBkU5dlMOkNg3urXBKAalsDvDBwOTi36o58PMz6LHJ+usg88uRWCTdvfH8JpI5MlXECSF5Qbv5vR3hF+842u6c1zMOIQpoIlxEINMlCozMZuhqLWox2/BAoGBAMqlGfqAr26xPOuLDoCi65ToA0g5U5WGtmDnK3XyZRTufLhWgVR57OnL39SIsGpg6s2qj0P/M+sytOtZs7KGy1od6jhMxYhYNIrbdvecSy6c2GaApYhvNmHcY3zWp8DUR8etvWuw2zPbtFF+fByR/sZWez6FcFA4zCn0ocHmVMVT";

    private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 初始化密钥
     *
     * @return
     */
    public static Map<String, Object> init() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 将密钥封装为map
            map = new HashMap<>();
            map.put(RSA_PUBLIC_KEY, publicKey);
            map.put(RSA_PRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            logger.error("初始化失败 : {}", e);
        }
        return map;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     */
    public static String sign(String data, String privateKey) {

        String str = "";
        try {
            // 解密由base64编码的私钥
            byte[] bytes = decryptBase64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取私钥对象
            PrivateKey key = factory.generatePrivate(pkcs);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data.getBytes());
            str = encryptBase64(signature.sign());
        } catch (Exception e) {
            logger.error("rsa 签名失败 : {}", e);
        }
        return str;
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(String data, String publicKey, String sign) {

        boolean flag = false;
        try {
            // 解密由base64编码的公钥
            byte[] bytes = decryptBase64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取公钥对象
            PublicKey key = factory.generatePublic(keySpec);
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data.getBytes());
            flag = signature.verify(decryptBase64(sign));
        } catch (Exception e) {
            logger.error("校验数字签名失败 : {}", e);
        }
        return flag;
    }

    /**
     * 私钥解密
     *
     * @param data 加密数据
     * @param key  私钥
     * @return
     */
    public static String decryptByPrivateKey(String data, String key) {

        try {
            // 对私钥解密
            byte[] bytes = decryptBase64(key);
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(Base64.decodeBase64(data));
            return new String(result);
        } catch (Exception e) {
            logger.error("解密失败 : {}", e);
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param data 加密数据
     * @param key  公钥
     * @return
     */
    public static String decryptByPublicKey(String data, String key) {

        try {
            // 对公钥解密
            byte[] bytes = decryptBase64(key);
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(Base64.decodeBase64(data));
            return new String(result);
        } catch (Exception e) {
            logger.error("解密失败 : {}", e);
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return
     */
    public static String encryptByPublicKey(String data, String key) {

        try {
            byte[] bytes = decryptBase64(key);
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(data.getBytes());
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            logger.error("公钥加密失败 : {}", e);
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  私钥
     * @return
     */
    public static String encryptByPrivateKey(String data, String key) {

        byte[] result = null;
        try {
            byte[] bytes = decryptBase64(key);
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            result = cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            logger.error("私钥加密失败 : {}", e);
        }
        return Base64.encodeBase64String(result);
    }

    /**
     * BASE64 解密
     *
     * @param key 需要解密的字符串
     * @return 字节数组
     */
    public static byte[] decryptBase64(String key) {

        return Base64.decodeBase64(key);
    }

    /**
     * BASE64 加密
     *
     * @param key 需要加密的字节数组
     * @return 字符串
     */
    public static String encryptBase64(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {

        logger.info("公钥加密--------私钥解密 -----------------------------------");
        String word = "你好，世界！Salt";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("abc","11223 3abc");
        word = jsonObject.toJSONString();
        String encWord = encryptByPublicKey(word, PUBLIC_KEY);

        String decWord = decryptByPrivateKey(encWord, PRIVATE_KEY);
        logger.info("加密前: {}", word);
        logger.info("加密后: {}", encWord);
        logger.info("解密后: {}", decWord);

        logger.info("私钥加密--------公钥解密--------------------------------");
        String english = "Hello, World!";
        String encEnglish = encryptByPrivateKey(english, PRIVATE_KEY);
        String decEnglish = decryptByPublicKey(encEnglish, PUBLIC_KEY);
        logger.info("加密前: {}" , english );
        logger.info("解密后: {}" , decEnglish);
        logger.info("私钥签名——公钥验证签名");

        // 产生签名
        String sign = sign(encEnglish, PRIVATE_KEY);
        logger.info("签名: {}" , sign);

        // 验证签名
        boolean status = verify(encEnglish, PUBLIC_KEY, sign);
        logger.info("状态: {}" , status);

        String pa = "aicWaO9yiewvGmqneUJWIcHl33J/MDRQ8bvkqmCrHKR54hsigH04qYbLjZ/fobXRPP7zt9ksL/LcqbTrxCFojHcDChMa0JO1D+YX7JxygDGZyJdWdAcsqwochlfSY+BPXrok79dsuQ51jl8yh6lg0XoRBjwXEnmIGu9Z/rsWOW96X2Jtqid+aqMVa1WuSMHJodzNDidCJo56W56K7VHUxY9PoITdMyxoq0X+wuFV1Hv/VIzqgJk5yEEV0HO4l7iG33/VOV8UREYd7M+tUjOT/IhCyiJ/hj42Zj/1P/RYIS0bzWu5fEibsnucym4/LWEjADJfImGqMT9PctBpuj1GQQ==";
        System.out.println("解密后："+decryptByPrivateKey(pa,PRIVATE_KEY));
    }
}
