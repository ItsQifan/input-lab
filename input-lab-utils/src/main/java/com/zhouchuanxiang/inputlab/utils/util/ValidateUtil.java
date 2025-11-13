package com.zhouchuanxiang.inputlab.utils.util;
import com.zhouchuanxiang.inputlab.common.MessageHelper;
import com.zhouchuanxiang.inputlab.common.exception.BizException;
import com.zhouchuanxiang.inputlab.common.exception.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 *  校验工具
 */
public class ValidateUtil {
    private static final ValidateUtil validate = new ValidateUtil();

    public static ValidateUtil validate(boolean flag, String message) {
        if (flag) throw new BizException(ErrorCodeEnum.UNEXPECTED_ERROR, message);
        return validate;
    }

    public static ValidateUtil validate(boolean flag, String message, Object... args) {
        ValidateUtil.paramValidate(flag, ErrorCodeEnum.UNEXPECTED_ERROR, MessageHelper.formatMsg(message, args));
        return validate;
    }

    /**
     * 参数校验方法
     *
     * @param expression 判断条件,不符合则报错
     * @param errorCodeEnum  错误类型
     * @param extMsg     错误附加信息
     */
    public static void paramValidate(boolean expression, ErrorCodeEnum errorCodeEnum, String extMsg) {
        if (expression) {
            throw new BizException(errorCodeEnum, extMsg);
        }
    }

    /**
     * 参数校验方法
     *
     * @param expression 判断条件,不符合则报错
     * @param extMsg     错误附加信息
     */
    public static void paramValidate(boolean expression, String extMsg) {
        if (expression) {
            throw new BizException(ErrorCodeEnum.BLANK_IS_ILLEGAL_PARAM, extMsg);
        }
    }

    /**
     * 参数校验方法
     *
     * @param expression 判断条件,不符合则报错
     * @param errorCodeEnum  错误类型
     */
    public static void paramValidate(boolean expression, ErrorCodeEnum errorCodeEnum) {
        paramValidate(expression, errorCodeEnum, errorCodeEnum.getDesc());
    }

    /**
     * 参数校验方法
     * <p>判断字符串是否为空为null</>
     *
     * @param str       需要判断的字符串
     * @param errorCodeEnum 错误类型
     * @param extMsg    错误附加信息
     */
    public static void validateString(String str, ErrorCodeEnum errorCodeEnum, String extMsg) {
        if (StringUtils.isBlank(str)) {
            throw new BizException(errorCodeEnum, extMsg);
        }
    }

    /**
     * 参数校验方法
     * <p>判断字符串是否为空为null,返回固定异常编码（"BLANK_IS_ILLEGAL_PARAM"）</>
     *
     * @param str    需要判断的字符串
     * @param extMsg 错误附加信息
     */
    public static void validateString(String str, String extMsg) {
        validateString(str, ErrorCodeEnum.BLANK_IS_ILLEGAL_PARAM, extMsg);
    }

    public static void validateBoolean(Boolean flag, ErrorCodeEnum errorCodeEnum, String extMsg) {
        if (flag) {
            throw new BizException(errorCodeEnum, extMsg);
        }
    }
}
