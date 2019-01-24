package com.jd.wyjm.remote.client.adapter;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;
import com.jd.wyjm.remote.client.channel.ClientChannel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端适配器
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Slf4j
public class ClientAdapter extends ChannelHandlerAdapter {
    /**
     * 方法元信息
     */
    private MethodInvokeMeta methodInvokeMeta;

    /**
     * Nio客户端渠道
     */
    private ClientChannel clientChannel;

    /**
     * 客户端渠道适配器句柄
     * @param methodInvokeMeta
     * @param clientChannel
     */
    public ClientAdapter(MethodInvokeMeta methodInvokeMeta, ClientChannel clientChannel) {
        this.methodInvokeMeta = methodInvokeMeta;
        this.clientChannel = clientChannel;
    }

    /**
     * 出现异常，打印异常数据信息。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 发送客户端数据信息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(methodInvokeMeta);
    }

    /**
     * 阅读渠道返回数据，并设置响应结果
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        clientChannel.setResponse(msg);
    }

}
