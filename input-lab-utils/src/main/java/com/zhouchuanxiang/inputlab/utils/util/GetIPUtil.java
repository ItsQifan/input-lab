package com.zhouchuanxiang.inputlab.utils.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-12-4
 * Time: 下午6:10
 */
public class GetIPUtil {
    private static final Logger logger = LoggerFactory.getLogger(GetIPUtil.class);
    private static volatile String ipAddress = "";
    private static volatile String ip6Address = "";
    private static volatile String localIpAddress = "";

    private static final String UNKNOWN = "unKnown";


    private GetIPUtil() {
    }

    /**
     * 获取本机IP地址
     *
     * @return
     */
    public static String getLocalIPAddress() {
        if (StringUtils.isNotBlank(localIpAddress)) {
            return localIpAddress;
        }

        try {
            InetAddress address = InetAddress.getLocalHost();
            localIpAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            logger.warn("获取本机IP失败,{}", e);
        }
        return localIpAddress;
    }

    /**
     * 获取本机IP地址
     * 多个网卡将有多个ip
     *
     * @return
     */
    public static String getLocalIPAddresses() {
        if (StringUtils.isNotBlank(ipAddress)) {
            return ipAddress;
        }

        try {
            StringBuilder ifConfig = new StringBuilder();
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface networkInterface = en.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        ifConfig.append(inetAddress.getHostAddress() + "--");
                    }
                }
            }
            ipAddress = ifConfig.substring(0, ifConfig.length() - 2);
        } catch (SocketException e) {
            logger.warn("获取本机IP失败,{}", e);
        }

        return ipAddress;
    }

    /**
     * 获取本机IPV6地址
     *
     * @return
     */
    public static String getLocalIPV6Address() {
        if (StringUtils.isNotBlank(ip6Address)) {
            return ip6Address;
        }

        try {
            ip6Address = getLocalIPv6Addr();
            logger.info(ip6Address);
        } catch (Exception e) {
            logger.warn("获取本机IPV6地址失败,{}", e);
        }
        return ip6Address;
    }


    /**
     * 获取IPV6地址
     * <p>
     * 获取eth0 IP地址，如果没有eth0,则取第一个获取到的IPV6地址
     * </p>
     *
     * @return IPV6地址
     * @throws SocketException
     */
    public static String getLocalIPv6Addr() throws SocketException {
        String ipv6addr = null;
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                InetAddress ip = ips.nextElement();
                if (ip instanceof Inet6Address&& !isReservedAddr(ip)) {
                    if (StringUtils.equals("eth0", ni.getName()) || null == ipv6addr) {
                        String ipAddr = ip.getHostAddress();
                        //过滤网卡
                        int index = ipAddr.indexOf('%');
                        if(index>0) {
                            ipAddr = ipAddr.substring(0, index);
                        }

                        if (StringUtils.equals("eth0", ni.getName())) {
                            return ipAddr;
                        } else {
                            ipv6addr = ipAddr;
                        }
                    }
                }
            }
        }
        return ipv6addr;
    }

    private static boolean isReservedAddr(InetAddress inetAddr) {
        if (inetAddr.isAnyLocalAddress() || inetAddr.isLinkLocalAddress() || inetAddr.isLoopbackAddress()) {
            return true;
        }
        return false;
    }


    /**
     * 获取真实IP
     * @param request
     * @return
     */
    public static String getRealIpAddress(HttpServletRequest request) {
        return request.getHeader("X-Real-IP");
    }

    /**
     * 代理IP判断
     * @param request
     * @return
     */
    public static String getForwardIpAddress(HttpServletRequest request) {
        String xFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xFor) && !UNKNOWN.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                xFor= xFor.substring(0, index);
            }
        }
        return xFor;
    }

    /**
     * 获取登录用户的IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
