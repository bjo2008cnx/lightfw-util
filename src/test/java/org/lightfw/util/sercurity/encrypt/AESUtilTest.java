package org.lightfw.util.sercurity.encrypt;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class AESUtilTest {

    @Test
    @Ignore
    public void testEncrypt() throws Exception {
        String content = "习大大是个好大大";
        String password = "1234%@#$%s";
        // 加密AFDADF
        System.out.println("加密前：" + content);
        byte[] encryptResult = AESUtil.encrypt(content, password);
        // 解密
        byte[] decryptResult = AESUtil.decrypt(encryptResult, password);
        String decrypted = new String(decryptResult);
        System.out.println("解密后：" + decrypted);
        assertEquals(content, decrypted);
    }

    @Test
    @Ignore
    public void testDecrypt() throws Exception {
        String content = "#AFDA#AFDxe#QRA923X";
        String password = "1234%@#$%s";
        // 加密
        System.out.println("加密前：" + content);
        byte[] encryptResult = AESUtil.encrypt(content, password);
        // 解密
        byte[] decryptResult = AESUtil.decrypt(encryptResult, password);
        String decrypted = new String(decryptResult);
        System.out.println("解密后：" + decrypted);
        assertEquals(content, decrypted);
    }

}