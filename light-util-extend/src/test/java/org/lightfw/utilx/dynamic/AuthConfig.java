package org.lightfw.utilx.dynamic;

import lombok.Data;


/**
 * 认证相关的配置
 *
 * @author Michael.Wang
 * @date 2017/8/21
 */
@Data
public class AuthConfig {

    private int appId;
    private String toAuthUrl;
    private boolean isSwitchOn;
    private Boolean isClosable;
    private Integer count;

}