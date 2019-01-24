package com.jd.wyjm.remote.client.core;

import com.jd.wyjm.remote.beans.MethodInvokeMeta;
import com.jd.wyjm.remote.client.channel.ClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *
 * 客户端远程调用实体类
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Component
public class ClientRemoteCall implements InitializingBean {
    /**
     * 初始化客户端的配置类
     */
    private Bootstrap bootstrap;
    /**
     * 客户端线程池
     */
    private EventLoopGroup worker;


    /**
     * 远程调用
     *
     * @param method
     * @param ip
     * @param port
     * @return
     */
    public Object call(final MethodInvokeMeta method, String ip,int port) {
        try {
            //创建一个客户端渠道。
            ClientChannel channel = new ClientChannel(method);
            //设置配置渠道句柄
            bootstrap.handler(channel);
            //连接服务器
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            channelFuture.channel().closeFuture().sync();
            //返回响应结果
            return channel.getResponse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源
     */
    @PreDestroy
    public void close() {
        worker.shutdownGracefully();
    }

    /**
     * 初始化客户端的配置参数
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //实例化配置类
        bootstrap = new Bootstrap();
        //设置客户端线程池
        worker = new NioEventLoopGroup();
        bootstrap.group(worker);
        //设置渠道
        bootstrap.channel(NioSocketChannel.class);
    }
}
