package com.zhouchuanxiang.inputlab.common;

/**
 * Project: ficop.app.action
 * <p>
 * File Created at May 12, 2013
 * $Id$
 * <p>
 * Copyright 2013 Alibaba.com Corporation Limited.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */


import java.text.MessageFormat;

/**
 * 消息资源格式化处理，比如占位符的替换等
 */
public class MessageHelper {



    /**
     * 格式化含占位符的字符串
     * <p>
     * 举例:
     *
     * <pre>
     * <ol><li>
     * 所有占位符参数都存在：
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:&quot;yimin.jiang&quot;, &quot;female&quot;
     *  return:My name is yimin.jiang,my sex is male
     * </li>
     *  <li>
     * 占位符参数数组不为null(含数组所有元素为null的情况，占位符都会被null取代):
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:null, &quot;female&quot;
     *  return:My name is null,my sex is male
     *
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:null, null
     *  return:My name is null,my sex is null
     * </li>
     * <li>
     * 占位符参数数组为null，占位符原样输出:
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:null
     *  return:My name is {0},my sex is {1}
     * </li>
     *  </ol>
     * </pre>
     *
     * @param msgTemplate    含占位符的字符串消息模板
     * @param positionValues 消息占位符取代变量，按参数顺序依次取代{0},{1},{2}...等
     * @return 用positionValues替换占位符后的字符串
     * @see MessageFormat#format(String, Object...)
     */
    public static String formatMsg(String msgTemplate, Object... positionValues) {
        try {
            return MessageFormat.format(msgTemplate, positionValues);
        } catch (Exception e) {
            StringBuilder buf = new StringBuilder("资源信息占位符替换异常，占位符参数信息：");
            for (int i = 0; i < positionValues.length; i++) {
                buf.append(" arg[" + i + "]=" + positionValues[i]);
            }
        }
        return msgTemplate;
    }

}
