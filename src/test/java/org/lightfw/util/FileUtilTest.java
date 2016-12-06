package org.lightfw.util;

import org.junit.Test;
import org.lightfw.util.io.common.FileUtil;

import java.io.File;

/**
 * FileUtilTest
 *
 * @author Wangxm
 * @date 2016/5/10
 */
public class FileUtilTest {
    @Test
    public void testWrite() {
        FileUtil.write(new File("d:/tmp/tmp/tmp.tmp"), "test", true);
    }
}
