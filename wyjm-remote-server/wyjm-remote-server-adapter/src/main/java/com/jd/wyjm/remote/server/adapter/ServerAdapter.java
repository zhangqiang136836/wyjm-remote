package com.jd.wyjm.remote.server.adapter;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;
import com.jd.wyjm.remote.server.dispatcher.ServerDispatcher;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过适配器进行方法适配
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Slf4j
@Sharable
@Component
public class ServerAdapter extends ChannelHandlerAdapter {

    /**
     * 请求内容分派器，将内容分派到指定的Service实例。
     */
    @Autowired
    private ServerDispatcher dispatcher;

    /**
     * 异常处理
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //打印异常数据信息
        cause.printStackTrace();
        //关闭渠道句柄
        ctx.close();
    }

    /**
     * 阅读渠道内容数据，调用分派器进行具体方法调用。
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //获取到方法元数据
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        // 屏蔽掉toString()方法
        if (invokeMeta.getMethodName().endsWith("toString()") && !"class java.lang.String".equals(invokeMeta.getReturnType().toString())) {
            log.info("客户端传入参数 :{},返回值：{}",invokeMeta.getArgs(), invokeMeta.getReturnType());
        }
        dispatcher.dispatcher(ctx, invokeMeta);
    }

}
