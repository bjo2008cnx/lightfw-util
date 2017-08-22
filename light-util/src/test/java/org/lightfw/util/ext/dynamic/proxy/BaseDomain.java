package org.lightfw.util.ext.dynamic.proxy;


import lombok.Data;

import java.sql.Timestamp;

/**
 * BaseDomain
 *
 * @author Michael.Wang
 * @date 2017/5/25
 */
@Data
public class BaseDomain {
    private long id;
    private Timestamp updateTime;
}