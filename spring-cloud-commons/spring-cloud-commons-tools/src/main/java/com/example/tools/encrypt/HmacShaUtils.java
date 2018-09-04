package com.example.tools.encrypt;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HmacSha 加密工具
 * @author hzh 2018/8/2 13:27
 */
@Slf4j
public class HmacShaUtils {

    public static String sha256(String data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            log.error("HmacShaUtils.sha256 encode exception", e);
            return null;
        }
    }

    public static String sha512(String data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            log.error("HmacShaUtils.sha512 encode exception", e);
            return null;
        }
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String temp = null;
        for (int n = 0; b != null && n < b.length; n++) {
            temp = Integer.toHexString(b[n] & 0XFF);
            if (temp.length() == 1) {
                hs.append('0');
            }
            hs.append(temp);
        }
        return hs.toString().toUpperCase();
    }
}
