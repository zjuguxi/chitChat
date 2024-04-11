package com.chitchat.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Optional;

@Slf4j
public class IpAddressUtils {

    public static String getIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            log.error("Unable to get local IP Address", e);
            return null;
        }
    }

    public static String getFormattedIpAddress() {
        return Optional.ofNullable(getIpAddress())
                .map(IpAddressUtils::formatIpAddress)
                .orElse(null);
    }

    private static String formatIpAddress(String ipAddress) {
        StringBuilder formatted = new StringBuilder();
        for (String part : ipAddress.split("\\.")) {
            formatted.append(String.format("%03d", Integer.parseInt(part)));
        }
        return formatted.toString();
    }
}
