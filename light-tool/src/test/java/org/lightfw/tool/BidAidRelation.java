package org.lightfw.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * 表 o2o.primary_account_tel表
 *
 * @author Michael.Wang
 * @date 2017/9/17
 */
@Data
public class BidAidRelation implements Serializable {
    private int id;
    private long bid;
    private long aid;
    private String tel;
}