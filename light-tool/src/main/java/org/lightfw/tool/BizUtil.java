package org.lightfw.tool;

import third.ChildAccount;

/**
 * 业务工具
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class BizUtil {
    public static void main(String[] args) {
        ChildAccount vo = new ChildAccount();
        String str = ReadCSVCodeTool.genearete(vo);
        System.out.println(str);
    }
}