package org.lightfw.utilx.ext.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Michael.Wang
 * @date 2017/4/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillNumRule {
    private String bill;

    private List<BillNumRuleElement> rules;
}
