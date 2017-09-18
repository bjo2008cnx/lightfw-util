package org.lightfw.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 认证相关的配置 TODO 拆分成两个类似
 *
 * @author Michael.Wang
 * @date 2017/8/21
 */
@Data
public class ConfigExample implements Serializable {
    public static final String AUTH_PROPERTIES = "auth.properties";

    private String appId;
    private String toAuthUrl;
    private String alipayGateway;
    private String alipayPublicKey;
    private String appPrivateKey;
    private String callbackUrl; //阿里回调isv的地址
    private String o2oAuthListUrl; //o2o的授权列表页面
    private String appName;
    private String headImg;

    private static ConfigExample instance = load();

    public static ConfigExample getInstance() {
        return instance;
    }

    private static ConfigExample load() {
        Map prop = PropertyUtil.load(AUTH_PROPERTIES);
        ConfigExample config = PopulateUtil.map2Obj(prop, ConfigExample.class);
        return config;
    }
}