package egovframework.com.uat.uia.util;

import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

public class EgovClientIP {

    public static String getClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR",
                "X-Real-IP",
                "X-RealIP",
                "REMOTE_ADDR"
        };

        String ipAddr = null;
        for (String header : headers) {
            ipAddr = request.getHeader(header);
            if (!ObjectUtils.isEmpty(ipAddr) && !"unknown".equalsIgnoreCase(ipAddr)) {
                break;
            }
        }

        if (ObjectUtils.isEmpty(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
            ipAddr = request.getRemoteAddr();
        }

        return ipAddr;
    }

}
