package com.jd.wyjm.remote.client.channel;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;
import com.jd.wyjm.remote.beans.NullWritable;
import com.jd.wyjm.remote.client.adapter.ClientAdapter;
import com.jd.wyjm.remote.codec.ObjectCodec;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Nio客户端渠道
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class ClientChannel extends ChannelInitializer {
    /**
     * 方法元数据信息
     */
    private MethodInvokeMeta methodInvokeMeta;
    /**
     * 响应结果数据信息
     */
    private Object response;

    /**
     * 客户端渠道函数
     * @param methodInvokeMeta
     */
    public ClientChannel(MethodInvokeMeta methodInvokeMeta){
        this.methodInvokeMeta=methodInvokeMeta;
    }

    /**
     * 初始化Nio渠道数据信息
     * @param channel
     * @throws Exception
     */
    @Override
    protected void initChannel(Channel channel) throws Exception {
        //获取到渠道的管道
        ChannelPipeline pipeline = channel.pipeline();
        //编码器，它可以计算当前待发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头
        pipeline.addLast(new LengthFieldPrepender(2));
        //自定义长度解码器，阀
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        //消息内容序列化
        pipeline.addLast(new ObjectCodec());
        //设置客户端渠道适配器
        pipeline.addLast(new ClientAdapter(methodInvokeMeta,this));
    }

    /**
     * 获取到响应结果
     * @return
     */
    public Object getResponse() {
        if (response instanceof NullWritable) {
            return null;
        }
        return response;
    }

    /**
     * 设置响应结果
     * @param response
     */
    public void setResponse(Object response) {
        this.response = response;
    }
}
