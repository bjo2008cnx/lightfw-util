package org.lightfw.util.common.collection;

import org.junit.Test;
import org.lightfw.util.collection.PropertiesUtil;
import org.lightfw.util.io.common.FileUtil;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.*;

public class PropertiesUtilTest {

    @Test
    public void testLoadByLocal() throws Exception {
        String path = "e:/tmp/test.properties";
        File file = new File(path);
        FileUtil.write(file, "name=dashen\n".concat("age=88"), false);
        Properties prop = PropertiesUtil.load(path);
        assertEquals("dashen", prop.getProperty("name"));
        assertEquals("88", prop.getProperty("age"));
    }
}