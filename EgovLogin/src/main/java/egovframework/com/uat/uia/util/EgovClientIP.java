package egovframework.com.uat.uia.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@UtilityClass
@Slf4j
public class EgovClientIP {

    public static String getClientIp() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("GatewayJwtProvider.getClientIp Client Ip Invalid", e);
            return "0.0.0.0";
        }
    }

}
