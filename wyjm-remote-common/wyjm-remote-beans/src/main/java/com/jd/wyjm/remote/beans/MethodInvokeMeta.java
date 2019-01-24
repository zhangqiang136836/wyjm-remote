package com.jd.wyjm.remote.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * 方法请求元数据封装实体类
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Data
public class MethodInvokeMeta implements Serializable {
    /**
     * 接口名称
     */
    private Class<?> interfaceClass;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数数组列表
     */
    private Object[] args;
    /**
     * 返回值类型
     */
    private Class<?> returnType;
    /**
     * 参数类型数组列表
     */
    private Class<?>[] parameterTypes;
}
