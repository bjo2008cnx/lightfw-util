package org.lightfw.common;

import org.junit.Test;
import org.lightfw.util.sercurity.encrypt.Md5Util;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SecUtilTest {

    @Test
    public void testMd5() throws Exception {
        String str1 = "123456";
        String str2 = Md5Util.getMD5String(str1);
        assertEquals("计算错误", "e10adc3949ba59abbe56e057f20f883e", str2);
    }

    @Test
    public void testFileMD5() {
        String file = TestUtil.path + "cmdexe";
        assertEquals("ad7b9c14083b52bc532fba5948342b98", Md5Util.getFileMD5String(new File(file)));
    }
}