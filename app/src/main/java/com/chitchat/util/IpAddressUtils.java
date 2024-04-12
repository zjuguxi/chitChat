package com.chitchat.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

@Slf4j
public class IpAddressUtils {

    public static String getIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            String message = "Unable to obtain local host's IP address";
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }

    public static String getFormattedIpAddress() {
        return formatIpAddress(getIpAddress());
    }

    private static String formatIpAddress(String ipAddress) {
        StringBuilder formatted = new StringBuilder();
        for (String part : ipAddress.split("\\.")) {
            formatted.append(String.format("%03d", Integer.parseInt(part)));
        }
        return formatted.toString();
    }
}
