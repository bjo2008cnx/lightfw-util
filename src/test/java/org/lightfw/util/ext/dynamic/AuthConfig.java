package org.lightfw.util.ext.dynamic;

import lombok.Data;


/**
 * 认证相关的配置
 *
 * @author Michael.Wang
 * @date 2017/8/21
 */
@Data
public class AuthConfig {

    private String appId;
    private String toAuthUrl;
    private String alipayGateWay;
    private String alipayPublicKey;
    private String appPrivateKey;

}