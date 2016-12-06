
package org.lightfw.util.text;

import org.lightfw.constant.GlobalConstant;
import org.lightfw.util.io.common.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static final String DEFAULT_ENCODING = GlobalConstant.CharSets.DEFAULT_ENCODING;
    private Properties properties = null;

    public PropertiesUtil(String fileName) {
        this(fileName, DEFAULT_ENCODING);
    }

    public PropertiesUtil(Properties properties) {
        setProperties(properties);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public PropertiesUtil(String fileName, String encoding) {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);        // properties.load(Prop.class.getResourceAsStream(fileName));
            if (inputStream == null)
                throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
            properties = new Properties();
            properties.load(new InputStreamReader(inputStream, encoding));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PropertiesUtil(File file) {
        this(file, DEFAULT_ENCODING);
    }

    /**
     * 示例：PropertiesUtil prop = new PropertiesUtil(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * String userName = prop.get("userName");
     *
     * @param file
     * @param encoding
     */
    public PropertiesUtil(File file, String encoding) {
        if (file == null) {
            throw new IllegalArgumentException("File can not be null.");
        }
        if (file.isFile() == false) {
            throw new IllegalArgumentException("File not found : " + file.getName());
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            properties = new Properties();
            properties.load(new InputStreamReader(inputStream, encoding));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        } finally {
            StreamUtil.close(inputStream);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? Integer.parseInt(value) : defaultValue;
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? Long.parseLong(value) : defaultValue;
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
