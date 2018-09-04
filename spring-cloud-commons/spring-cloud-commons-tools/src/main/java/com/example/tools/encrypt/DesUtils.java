package com.example.tools.encrypt;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Des加密工具
 * @author hzh 2018/8/2 13:28
 */
@Slf4j
public class DesUtils {

    private final static String DES = "DES";
    private final static String ENCODE = "GBK";
    public final static String defaultKey = "H4ehXYFG";

    /**
     * 根据键值进行加密
     * @param data 需要加密的数据
     * @return 返回加密后的密文
     */
    public static String encrypt(String data) {
        try {
            return encrypt(data, defaultKey);
        } catch (Exception e) {
            log.error("DesUtils.encrypt Exception", e);
        }
        return null;
    }

    /**
     * 根据键值进行解密
     * @param data 需要解密的数据
     * @return 返回解密后的明文
     */
    public static String decrypt(String data) {
        if (data == null) {
            return null;
        }
        try {
            return decrypt(data, defaultKey);
        } catch (Exception e) {
            log.error("DesUtils.decrypt Exception", e);
        }
        return null;
    }

    /**
     * 根据键值进行加密
     * @param data 需要加密的数据
     * @param key 加密密钥
     * @return 返回加密后的密文
     * @throws Exception 加密异常
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        return Base64.getEncoder().encodeToString(bt);
    }

    /**
     * 根据键值进行解密
     * @param data 需要解密的数据
     * @param key 解密密钥
     * @return 返回解密后的明文
     */
    public static String decrypt(String data, String key) {
        if (data == null) {
            return null;
        }
        try {
            byte[] buf = Base64.getDecoder().decode(data);
            byte[] bt = decrypt(buf, key.getBytes(ENCODE));
            return new String(bt, ENCODE);
        } catch (Exception e) {
            log.error("DesUtils.decrypt Exception", e);
            return null;
        }
    }

    /**
     * 根据键值进行加密
     * @param data 需要加密的数据
     * @param key 加密密钥
     * @return 返回加密后的密文
     * @throws Exception 加密异常
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

        return cipher.doFinal(data);
    }

    /**
     * 根据键值进行解密
     * @param data 需要解密数据
     * @param key 解密密钥
     * @return 返回解密后的明文
     * @throws Exception 解密异常
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

        return cipher.doFinal(data);
    }
}
