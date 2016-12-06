package org.lightfw.util.sercurity.encrypt;

import java.util.zip.CRC32;

public class CRCUtil {

    /**
     * CRC校验是否正确
     *
     * @param crcCode String
     * @param content String
     * @return boolean
     */
    public static boolean isCRCEqual(String crcCode, String content) {
        if (getCRCValue(content).equalsIgnoreCase(crcCode)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取得CRC值(为16进制字符串)
     *
     * @param content String
     * @return String
     */
    public static String getCRCValue(String content) {
        long CRClong = 0;

        CRC32 crc = new CRC32();
        crc.update(content.getBytes());
        CRClong = crc.getValue();
        return (Long.toHexString(CRClong)).toUpperCase();
    }
}
