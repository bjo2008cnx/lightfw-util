package org.lightfw.util.text;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.constant.GlobalConstant;
import org.lightfw.util.io.common.StreamUtil;
import org.lightfw.util.validate.AssertUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 属性工具类
 */
@Slf4j
public class PropertiesUtil {
    public static final String ENCODING = GlobalConstant.Defaults.DEFAULT_ENCODING;
    private static final Properties EMTPTY_PROPERTIES = GlobalConstant.Collections.EMPTY_PROPERTIES;

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties load(String fileName) {
        AssertUtil.assertNotNullOrEmpty("file name", fileName);
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                return EMTPTY_PROPERTIES;
            }
            Properties properties = new Properties();
            properties.load(new InputStreamReader(in, ENCODING));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtil.close(in);
        }
    }
}
