package com.jd.wyjm.remote.enums;

/**
 * 响应结果处理枚举类
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public enum CodeEnum {
    /**
     * 处理成功
     */
    SUCCESS(200, "处理成功"),
    /**
     * 未知异常
     */
    UNKOWN_ERROR(999, "未知异常");

    /**
     * 响应代码
     */
    private final int code;

    /**
     * 响应消息
     */
    private final String message;

    CodeEnum(int code, String message) {

        this.code = code;
        this.message = message;

    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
