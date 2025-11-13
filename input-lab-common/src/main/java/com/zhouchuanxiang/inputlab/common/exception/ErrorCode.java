package com.zhouchuanxiang.inputlab.common.exception;

/**
 *
 * 错误码定义抽象类
 */
public interface ErrorCode {
    /**
     * 错误的错误码
     * @return
     */
    String getCode();
    /**
     * 错误的描述
     * @return
     */
    String getDesc();
}
