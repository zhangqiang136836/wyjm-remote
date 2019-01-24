package com.jd.wyjm.remote.server.dispatcher;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;
import com.jd.wyjm.remote.beans.NullWritable;
import com.jd.wyjm.remote.enums.CodeEnum;
import com.jd.wyjm.remote.reponse.CommonResult;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 将具体的请求方法分派到制定分服务器方法内
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Component
public class ServerDispatcher implements ApplicationContextAware, InitializingBean {

    /**
     * Spring的上下文内容,通过上下文可以取到IOC中的实例。
     */
    private ApplicationContext applicationContext;

    /**
     * 调用处理方法的线程池
     */
    private ThreadPoolExecutor executor = null;

    /**
     * 消息发送
     */
    public void dispatcher(final ChannelHandlerContext ctx, final MethodInvokeMeta invokeMeta) {
        /**
         * 提交消息发送
         */
        executor.submit(() -> {
            ChannelFuture channelFuture = null;
            try {
                //取得到接口名称
                Class<?> interfaceClass = invokeMeta.getInterfaceClass();
                //取得到方法名称
                String methodName = invokeMeta.getMethodName();
                //取得参数数组
                Object[] args = invokeMeta.getArgs();
                //取得参数类型
                Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
                //目标对象
                Object targetObject = applicationContext.getBean(interfaceClass);
                //目标对象方法
                Method method = targetObject.getClass().getMethod(methodName, parameterTypes);
                //调用对应方法
                Object obj = method.invoke(targetObject, args);
                if (obj == null) {
                    channelFuture = ctx.writeAndFlush(NullWritable.nullWritable());
                } else {
                    channelFuture = ctx.writeAndFlush(obj);
                }
                channelFuture.addListener(ChannelFutureListener.CLOSE);
            } catch (Exception ex) {
                CommonResult commonResult = new CommonResult(CodeEnum.UNKOWN_ERROR);
                channelFuture = ctx.writeAndFlush(commonResult);
                channelFuture.addListener(ChannelFutureListener.CLOSE);
            }
        });
    }

    /**
     * 实例化实体时，初始化线程池大小
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new ThreadPoolExecutor(
                10,
                100,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(10));
    }

    /**
     * 实例化实体时，自动注入Spring的上下文内容。
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
