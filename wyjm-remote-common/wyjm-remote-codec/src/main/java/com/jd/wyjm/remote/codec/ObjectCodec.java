package com.jd.wyjm.remote.codec;

import com.jd.wyjm.remote.utils.ObjectSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 消息解析器
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class ObjectCodec extends MessageToMessageCodec<ByteBuf, Object> {
    /**
     * 将消息内容序列化为字节列表
     * @param channelHandlerContext
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, List<Object> out) throws Exception {
        byte[] data = ObjectSerializer.serilizer(msg);
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(data);
        out.add(buf);
    }

    /**
     * 将字节数据反序列化为内容列表
     * @param channelHandlerContext
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> out) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        Object serilizer = ObjectSerializer.deSerilizer(bytes);
        out.add(serilizer);
    }
}
