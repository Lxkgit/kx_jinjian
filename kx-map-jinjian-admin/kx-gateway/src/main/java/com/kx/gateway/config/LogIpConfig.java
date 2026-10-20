package com.kx.gateway.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LogIpConfig extends ClassicConverter {

    private static final Logger logger = LoggerFactory.getLogger(LogIpConfig.class);
    private static String webIp;
    static {
        try {
            webIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            logger.error("获取日志ip异常", ex);
            webIp = null;
        }
    }

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return webIp;
    }
}
