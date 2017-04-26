package org.lightfw.utilx.ext.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Michael.Wang
 * @date 2017/4/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillNumRuleElement {
    /**
     * 规则类型，如常量，日期等
     */
    private String type;

    /**
     * 规则长度
     */
    private String length;

    /**
     * 规则的值，如果是常量，则设置该值；否则（如日期），可以不设置，系统自动生成
     */
    private String value;

    /**
     * 位数不足补齐字符
     */
//    private char padding;
}
