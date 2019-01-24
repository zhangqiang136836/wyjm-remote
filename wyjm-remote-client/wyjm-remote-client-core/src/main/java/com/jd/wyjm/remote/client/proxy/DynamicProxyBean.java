package com.jd.wyjm.remote.client.proxy;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;
import com.jd.wyjm.remote.client.core.ClientRemoteCall;
import com.jd.wyjm.remote.utils.WrapMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 */
@Slf4j
public class DynamicProxyBean extends AbstractFactoryBean implements InvocationHandler {
    /**
     * 请求服务器IP地址
     */
    private String  ip;
    /**
     * 请求服务器端口
     */
    private Integer port;
    /**
     * 接口类型
     */
    private Class interfaceClass;
    /**
     * 客户端调用
     */
    @Autowired
    private ClientRemoteCall clientRemoteCall;

    /**
     * 调取代理类方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //初始化方法参数
        final MethodInvokeMeta methodInvokeMeta = WrapMethod.readMethod(interfaceClass, method, args);
        //调取远程请求
        return clientRemoteCall.call(methodInvokeMeta,ip,port);
    }

    /**
     * 获取到接口类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    /**
     * 代理工厂，初始化需要代理的实体。
     * @return
     * @throws Exception
     */
    @Override
    protected Object createInstance() throws Exception {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
    }


    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setClientRemoteCall(ClientRemoteCall clientRemoteCall) {
        this.clientRemoteCall = clientRemoteCall;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
