package org.lightfw.utilx.text.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author Michael.Wang
 * @date 2017/4/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillNumRules {
    private List<BillNumRule> bills;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BillNumRule {
        private String bill;
        private List<BillNumRuleElement> rules;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BillNumRuleElement {
        private String type; //规则类型，如常量，日期等
        private int length; //规则长度
        private String value; //规则的值，如果是常量，则设置该值；否则（如日期），可以不设置，系统自动生成
    }
}






