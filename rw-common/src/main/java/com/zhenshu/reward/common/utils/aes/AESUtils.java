package com.zhenshu.reward.common.utils.aes;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

/**
 * @author jing
 * @version 1.0
 * @desc AES 加解密工具类
 * @date 2021/2/7 0007 11:28
 **/
public class AESUtils {

    private static final String MODE = "AES/ECB/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CHARSET = "utf-8";

    /**
     * 加密
     *
     * @param content 内容
     * @param key     秘钥
     * @return 加密后的数据
     */
    public static String encrypt(String content, String key) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance(MODE);
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(CHARSET), KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        byte[] encrypt = cipher.doFinal(content.getBytes());
        encrypt = Base64.getEncoder().encode(encrypt);
        return new String(encrypt, CHARSET);
    }

    /**
     * 解密数据
     *
     * @param content 内容
     * @param key     秘钥
     * @return 数据
     */
    public static String decrypt(String content, String key) throws GeneralSecurityException, IOException {
        // 替换base64里的换行
        content = content.replaceAll("[\\n\\r]", "");
        byte[] data = Base64.getDecoder().decode(content.getBytes(CHARSET));

        Cipher cipher = Cipher.getInstance(MODE);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET), KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] result = cipher.doFinal(data);
        return new String(result);
    }


}
