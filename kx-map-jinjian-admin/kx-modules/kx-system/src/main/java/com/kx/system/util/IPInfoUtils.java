package com.kx.system.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取服务器IP地址
 */
public class IPInfoUtils {

    public static String getServerIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress == null ? "" : inetAddress.getHostAddress();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
