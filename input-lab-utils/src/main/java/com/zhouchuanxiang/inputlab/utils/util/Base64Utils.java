package com.zhouchuanxiang.inputlab.utils.util;


import org.apache.commons.codec.binary.Base64;

/**
 * @author zhouchuanxiang
 * @date 2025/9/28 11:00
 * @description BASE64编码解码工具包
 * @version 1.0
 */
public class Base64Utils {

    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decodeBase64(base64.getBytes());
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return Base64.encodeBase64String(bytes);
    }


}
