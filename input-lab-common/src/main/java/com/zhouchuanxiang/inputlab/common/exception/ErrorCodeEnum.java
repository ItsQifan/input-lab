package com.zhouchuanxiang.inputlab.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName ErrorCode
 * @Description 错误类型
 * @Author zhouchuanxiang
 * @Date 2025/11/11 16:50
 * @Version 1.0
 */
public enum ErrorCodeEnum implements ErrorCode {
    /**
     * 启动异常
     */
    START_ERROR("START_ERROR", "启动异常"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("999999", "系统异常"),

    /**
     * 操作成功
     */
    SUCCESS("000000", "操作成功"),

    /**
     * 操作不合法, 不允许操作
     */
    OPERATION_NOT_ALLOWED("OPERATION_NOT_ALLOWED", "操作不合法, 不允许操作"),

    /**
     * 结果不符合预期
     */
    RESULT_IS_UNEXPECTED("RESULT_IS_UNEXPECTED", "结果不符合预期"),

    /**
     * 数据量太大,导致溢出
     */
    RESULT_DATA_OVERFLOW("RESULT_DATA_OVERFLOW", "数据量太大,导致溢出"),

    /**
     * 业务渠道不合法
     */
    BIZ_CHANNEL_WRONG("BIZ_CHANNEL_WRONG", "业务渠道错误"),

    /**
     * 不支持的参数设置
     */
    CONFIG_NOT_SUPPORT("CONFIG_NOT_SUPPORT", "不支持的参数设置"),

    /**
     * 不支持的取值
     */
    VALUE_NOT_SUPPORT("VALUE_NOT_SUPPORT", "不支持的取值"),

    /**
     * 参数为空（含null）是非法的
     */
    BLANK_IS_ILLEGAL_PARAM("BLANK_IS_ILLEGAL_PARAM", "参数为空（含null）是非法的"),
    /**
     * 参数非法
     */
    ILLEGAL_PARAM("ILLEGAL_PARAM", "参数非法"),
    /**
     * 远程服务调用返回失败
     */
    REMOTE_SERVICE_INVOKE_FAIL("REMOTE_SERVICE_INVOKE_FAIL", "远程服务调用返回失败"),
    /**
     * 服务参数非法
     */
    ILLEGAL_SERVICE("ILLEGAL_SERVICE", "服务参数非法"),
    /**
     * 这种情况一般是捕获了没有单独进行catch处理的异常然后设定的错误码
     */
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "非预期的系统错误"),

    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "对象未找到"),

    OBJECT_IS_EXISTED("OBJECT_IS_EXISTED", "对象已存在"),

    UNIQUE_INSERT_ERROR("UNIQUE_INSERT_ERROR", "插入幂等错误"),

    /**
     * 获取锁超时
     */
    REDIS_LOCK_TIMEOUT("REDIS_LOCK_TIMEOUT", "获取锁超时"),
    /**
     * 数据未找到
     */
    DATA_NOT_FOUND("DATA_NOT_FOUND", "数据未找到"),
    /**
     * 状态不支持
     */
    STATUS_NOT_SUPPORTED("STATUS_NOT_SUPPORTED", "状态不支持"),

    ;
    /**
     * 处理结果码
     */
    private final String code;

    /**
     * 处理结果描述
     */
    private final String desc;

    /**
     * 构造函数
     *
     * @param code
     * @param desc
     */
    ErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code 如果为null，返回null
     * @return 不匹配返回<code>null</code>
     */
    public static ErrorCodeEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (ErrorCodeEnum result : values()) {
                if (result.getCode().equals(code)) {
                    return result;
                }
            }
        }
        return UNEXPECTED_ERROR;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
