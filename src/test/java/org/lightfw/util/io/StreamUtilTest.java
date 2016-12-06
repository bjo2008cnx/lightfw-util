package org.lightfw.util.io;

import org.junit.Test;
import org.lightfw.util.io.common.StreamUtil;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;

public class StreamUtilTest {

    @Test
    public void testClose() throws Exception {
        FileReader reader = new FileReader(new File("d:/test.txt"));
        StreamUtil.close(reader);
        Closeable closeable = new FileReader(new File("d:/test.txt"));
    }
}