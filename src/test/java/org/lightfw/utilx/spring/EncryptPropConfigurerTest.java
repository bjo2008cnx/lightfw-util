package org.lightfw.utilx.spring;

import org.junit.Test;
import org.lightfw.util.io.common.FileUtil;

import java.io.File;
import java.io.IOException;

public class EncryptPropConfigurerTest {

    /**
     * 改成TestCase
     *
     * @param args
     */
    public static void main(String[] args) {


    }

    @Test
    public void testEncrypt() throws Exception {
        String pathName = "jdbc.properties";
        File file = FileUtil.getClasspathFile(pathName);
        new EncryptPropConfigurer().encrypt(file);
    }
}