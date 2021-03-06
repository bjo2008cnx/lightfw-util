package org.lightfw.common;

import org.lightfw.util.system.SysInfoUtil;
import org.lightfw.util.ext.io.FileZipUtil;
import org.junit.Test;

import java.io.File;

public class FileZipUtilTest {

    @Test
    public void testDeCompress() throws Exception {
        String file = SysInfoUtil.CURRENT_USER_DIR + "/src/test/java/model";
        String zipFile = TestUtil.path + "temp/test.zip";
        FileZipUtil.deCompress(new File(file), zipFile);
    }

    @Test
    public void testUnCompress() throws Exception {
        String zipFile = TestUtil.path + "temp/test.zip";
        if (!(new File(zipFile).exists())) {
            testDeCompress();
        }
        FileZipUtil.unCompress(new File(zipFile), TestUtil.path + "temp/unzip/");
    }
}