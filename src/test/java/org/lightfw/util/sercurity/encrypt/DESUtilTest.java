package org.lightfw.util.sercurity.encrypt;

import org.junit.Test;

import static org.junit.Assert.*;

public class DESUtilTest {

    @Test
    public void testEncryptString() throws Exception {
        String content = "习大大是个好大大";
        String password = "11111111";
        // 加密AFDADF
        System.out.println("加密前：" + content);
        String encryptResult = DESUtil.encrypt(content, password);
        System.out.println("加密后：" + encryptResult);
        // 解密
        String decryptResult = DESUtil.decrypt(encryptResult, password);
        System.out.println("解密后：" + decryptResult);
        assertEquals(content, decryptResult);
    }

    @Test
    public void testEncryptBytes() throws Exception {
        String content = "习大大是个好大大";
        String password = "1234%@#$";
        // 加密AFDADF
        System.out.println("加密前：" + content);
        byte[] encryptResult = DESUtil.encrypt(content.getBytes(), password.getBytes());
        // 解密
        byte[] decryptResult = DESUtil.decrypt(encryptResult, password.getBytes());
        String decrypted = new String(decryptResult);
        System.out.println("解密后：" + decrypted);
        assertEquals(content, decrypted);
    }

}