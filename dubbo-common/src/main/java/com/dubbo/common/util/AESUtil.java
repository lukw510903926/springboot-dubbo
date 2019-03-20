package com.dubbo.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <p>
 * AES加密解密
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/27 17:08
 **/
public class AESUtil {

    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String KEY_ALGORITHM = "AES";

    /**
     * 默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/GCM/PKCS5Padding";

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {

        try {
            SecretKey key = getSecretKey(password);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] iv = cipher.getIV();
            assert iv.length == 12;
            byte[] encryptData = cipher.doFinal(content.getBytes());
            byte[] message = new byte[12 + content.getBytes().length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            return Base64.encodeBase64String(message);
        } catch (GeneralSecurityException ex) {
            logger.error("加密失败 : {}", ex);
        }
        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {

        try {
            SecretKey key = getSecretKey(password);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] msg = Base64.decodeBase64(content);
            GCMParameterSpec params = new GCMParameterSpec(128, msg, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, key, params);
            byte[] decryptData = cipher.doFinal(msg, 12, msg.length - 12);
            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("解密失败 : {}", ex);
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {

        try {
            //返回生成指定算法密钥生成器的 KeyGenerator 对象
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(password.getBytes()));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("生成加密秘钥 : {}", ex);
        }
        return null;
    }

    public static void main(String[] args) {

        String encrypt = encrypt("context msg", "password");
        logger.info(encrypt);
        logger.info(decrypt(encrypt, "password"));
    }

}