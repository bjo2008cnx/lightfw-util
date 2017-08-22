package org.lightfw.util.sercurity.encrypt;

import org.lightfw.util.lang.ExceptionUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DESUtil {

    private static String Algorithm = "DES";

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws java.io.IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) {
        if (data == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bt;
        try {
            byte[] buf = decoder.decodeBuffer(data);
            bt = decrypt(buf, key.getBytes());
        } catch (IOException e) {
            throw ExceptionUtil.transform(e);
        }
        return new String(bt);
    }

    /**
     * 数据加密
     *
     * @ param input byte[] 加密内容
     * @ param key byte[] 密钥
     * @ return byte[]
     */
    public static byte[] encrypt(byte[] input, byte[] key) {
        byte[] cipherByte;
        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        try {
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            cipherByte = c1.doFinal(input);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw ExceptionUtil.transform(e);
        }

        return cipherByte;
    }

    /**
     * 数据解密
     *
     * @ param input byte[]
     * @ param key byte[]
     * @ return byte[]
     */
    public static byte[] decrypt(byte[] input, byte[] key) {
        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        byte[] clearByte = null;
        try {
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            clearByte = c1.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clearByte;
    }
}
