package org.lightfw.constant;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全局常量
 *
 * @author Wangxm
 * @date 2016/5/7
 */
public interface GlobalConstant {

    /**
     * 字符集
     */
    public interface CharSets {
        public String UTF8 = "UTF8";
        public String GBK = "GBK";
        public String GB2312 = "GB2312";
        public String DEFAULT_ENCODING = UTF8;
    }

    /**
     * 默认空集合
     */
    public interface Collections {
        public List EMPTY_LIST = java.util.Collections.EMPTY_LIST; //不可变列表，用于返回空列表的场景
        public Map EMPTY_MAP = java.util.Collections.EMPTY_MAP; //不可变Map，用于返回空Map的场景
        public Set EMPTY_SET = java.util.Collections.EMPTY_SET; //不可变Set，用于返回空Set的场景
    }

    /**
     * 默认异常
     */
    public interface Exceptions {
        public RuntimeException TODO = new RuntimeException("TODO");
        public IllegalArgumentException ARGUMENT_EMPTY = new IllegalArgumentException("Parameter cannot be null");
    }

    /**
     * 默认布尔型
     */
    public interface Booleans {
        public int TRUE_INT = 1;
        public int FALSE_INT = 0;
        public String TRUE_STR = String.valueOf(TRUE_INT);
        public String FALSE_STR = String.valueOf(FALSE_INT);
    }
}
