package org.lightfw.util.system;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 网络工具
 */
public class LocalAddressUtil {

    /**
     * 获取本地ip
     * @return
     */
    public static String getLocalIp() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignore) {
            ip = null;
        }
        return ip;
    }

    /**
     * 获取本地host name
     * @return
     */
    public static String getLocalHostName() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignore) {
            hostName = "";
        }
        return hostName;
    }


    /**
     * 获取第一个IP地址
     *
     * @return 第一个IP地址
     * @throws SocketException 网络异常
     */
    public static InetAddress getLocalInetAddress() {
        try {
            //取全部网络接口
            Enumeration<NetworkInterface> enu = NetworkInterface.getNetworkInterfaces();
            while (enu.hasMoreElements()) {
                NetworkInterface ni = enu.nextElement();
                if (ni.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addressEnumeration = ni.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    InetAddress address = addressEnumeration.nextElement();
                    // ignores all invalidated addresses
                    if (address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isAnyLocalAddress()) {
                        continue;
                    }
                    return address;
                }
            }
        } catch (SocketException ignore) {
        }
        return null;
    }

    /**
     * 获取第2段和第4段ip，并合并成4位,用于特殊场景下的ip特征
     *
     * @return the string local address
     */

    public static String getLocalAddressParts() throws SocketException {
        String address = getLocalInetAddress().getHostAddress();
        if (address == null) {
            return "";
        }

        String[] parts = address.split("\\.");
        String second;
        String fourth;
        if (parts == null || parts.length == 0) {
            //under Wifi, ip is like:2001:0:9d38:6ab8:1444:650a:8b18:44a6
            String[] partsWifi = address.split(":");
            second = partsWifi[1].substring(0, 1);
            fourth = partsWifi[3].substring(0, 1);
        } else {
            second = parts[1];
            fourth = parts[3];
        }
        second = second.length() >= 3 ? second.substring(0, 2) : second;
        fourth = fourth.length() >= 3 ? fourth.substring(0, 2) : fourth;
        return second + fourth;
    }
}
