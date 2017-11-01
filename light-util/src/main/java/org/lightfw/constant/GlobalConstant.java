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
    interface CharSets {
        String UTF8 = "UTF8";
        String GBK = "GBK";
        String GB2312 = "GB2312";
        String ASCII = "US-ASCII";
        String UTF16 = "UTF-16";
        String UNICODE = "UNICODE";
        String ISO88591 = "ISO-8859-1";
        String DEFAULT_ENCODING = UTF8;
    }

    /**
     * 默认空集合
     */
    interface Collections {
        List EMPTY_LIST = Collections.EMPTY_LIST; //不可变列表，用于返回空列表的场景
        Map EMPTY_MAP = Collections.EMPTY_MAP; //不可变Map，用于返回空Map的场景
        Set EMPTY_SET = Collections.EMPTY_SET; //不可变Set，用于返回空Set的场景
    }

    /**
     * 默认异常
     */
    interface Exceptions {
        RuntimeException TODO = new RuntimeException("TODO");
        IllegalArgumentException ARGUMENT_EMPTY = new IllegalArgumentException("Parameter cannot be null");
    }

    /**
     * 默认布尔型
     */
    interface Booleans {
        int TRUE_INT = 1;
        int FALSE_INT = 0;
        String TRUE_STR = String.valueOf(TRUE_INT);
        String FALSE_STR = String.valueOf(FALSE_INT);
    }
}
