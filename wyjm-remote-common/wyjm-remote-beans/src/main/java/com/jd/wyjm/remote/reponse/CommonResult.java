package com.jd.wyjm.remote.reponse;

import com.jd.wyjm.remote.enums.CodeEnum;

/**
 * 响应结果处理通用类
 *
 * @param <T>
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class CommonResult<T> {
    /**
     * 应答码
     *
     */
    private int code = CodeEnum.SUCCESS.getCode();
    /**
     * 应答码描述
     *
     */
    private String message = CodeEnum.SUCCESS.getMessage();
    /**
     * 应答数据体
     *
     */
    private T data;

    public CommonResult() {
        this(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), null);
    }

    public CommonResult(CodeEnum codeEnum) {
        this(codeEnum.getCode(), codeEnum.getMessage(), null);
    }

    /**
     * 默认返回的code=CodeEnum.SUCCESS.getCode()
     */
    public CommonResult(T data) {
        this(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 默认返回的data=null
     */
    public CommonResult(int code, String message) {
        this(code, message, null);
    }

    public CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 判断返回结果是否成功
     */
    public boolean success() {
        return code == CodeEnum.SUCCESS.getCode();
    }
}
