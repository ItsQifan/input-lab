package com.zhouchuanxiang.inputlab.common.exception;


import com.zhouchuanxiang.inputlab.common.MessageHelper;

/**
 * BizException
 * 业务类型异常
 * 复用时，可以将Biz替换为具体的业务单词
 */
public class BizException extends RuntimeException {

    /**
     * 错误码信息
     */
    private ErrorCode errorCode;

    /**
     * 除了错误码本身描述的提示信息外，额外补充的信息
     */
    private String extraMsg;
    /**
     * 错误信息模板
     */
    private static final String MSG_TEMPLATE = "错误码:{0}, 描述:{1}, 异常信息:{2}";


    public BizException(ErrorCode errorCode, String extraMsg, Throwable cause) {
        super(getMessage(errorCode, extraMsg), cause);
        this.errorCode = errorCode;
        this.extraMsg = extraMsg;
    }

    public BizException(ErrorCode errorCode, String extraMsg) {
        super(getMessage(errorCode, extraMsg));
        this.errorCode = errorCode;
        this.extraMsg = extraMsg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    private static String getMessage(ErrorCode errorCode, String extraMessage) {
        extraMessage = (null != extraMessage) ? extraMessage : "";
        return MessageHelper.formatMsg(MSG_TEMPLATE, errorCode.getCode(), errorCode.getDesc(), extraMessage);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
