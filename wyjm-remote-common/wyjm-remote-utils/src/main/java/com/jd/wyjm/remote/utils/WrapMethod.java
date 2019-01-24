package com.jd.wyjm.remote.utils;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;

import java.lang.reflect.Method;

/**
 * 方法元数据信息
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class WrapMethod {
    /**
     * 获取到方法的元数据实体
     *
     * @param interfaceClass
     * @param method
     * @param args
     * @return
     */
    public static MethodInvokeMeta readMethod(Class interfaceClass, Method method, Object[] args) {
        //实例化一个方法
        MethodInvokeMeta methodInvokeMeta = new MethodInvokeMeta();
        //设置接口名称
        methodInvokeMeta.setInterfaceClass(interfaceClass);
        //设置参数
        methodInvokeMeta.setArgs(args);
        //设置方法名称
        methodInvokeMeta.setMethodName(method.getName());
        //设置返回类型
        methodInvokeMeta.setReturnType(method.getReturnType());
        //设置参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        methodInvokeMeta.setParameterTypes(parameterTypes);
        //返回实体
        return methodInvokeMeta;
    }
}
