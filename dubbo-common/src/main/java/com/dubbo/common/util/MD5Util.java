package com.dubbo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类的说明：MD5加密工具类
 **/
@Slf4j
public class MD5Util {


    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String Md5(String convertStr) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        byte[] bytes = digest.digest(convertStr.getBytes(StandardCharsets.UTF_8));
        return String.format("%032x", new BigInteger(1, bytes));
    }

    public static String MD5Encode(String origin, String charSet) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isEmpty(charSet)) {
                origin = byteArrayToHexString(md.digest(origin.getBytes()));
            } else {
                origin = byteArrayToHexString(md.digest(origin.getBytes(charSet)));
            }
        } catch (Exception exception) {
            log.error("加密失败 : {}", exception);
        }
        return origin;
    }

    private static String byteArrayToHexString(byte[] bytes) {

        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(byteToHexString(b));
        }
        return builder.toString();
    }

    private static String byteToHexString(byte b) {

        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
