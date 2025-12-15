package com.zhouchuanxiang.inputlab.common.enums;

import lombok.Getter;

/**
 * 任务状态 状态:1-成功；0-失败；2-执行中
 */
@Getter
public enum JobEnum {


    YES("1","成功"),

    NO("0","失败"),

    ING("2","执行中"),
 ;

    JobEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public static JobEnum getByName(String code) {
        for (JobEnum type : JobEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
