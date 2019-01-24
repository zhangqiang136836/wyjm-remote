package com.jd.wyjm.remote.server.core;

import com.jd.wyjm.remote.codec.ObjectCodec;
import com.jd.wyjm.remote.server.adapter.ServerAdapter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *
 * CommandLineRunner 服务启动执行，执行加载数据。
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {
    /**
     * 初始化服务器配置和启动类
     */
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    /**
     * 实例化一个Boss的Nio线程组，用来处理nio的Accept。
     */
    private final EventLoopGroup parentLoopGroup = new NioEventLoopGroup();
    /**
     * 实例化一个Worker的Nio线程组，用来处理nio的Read和Write
     */
    private final EventLoopGroup childLoopGroup  = new NioEventLoopGroup();
    /**
     * 服务适配器
     */
    @Autowired
    private ServerAdapter adapter;

    @PreDestroy
    public void close() {
        log.info("关闭服务器.....");
        //退出，关闭parentLoopGroup线程组
        parentLoopGroup.shutdownGracefully();
        //退出，关闭childLoopGroup线程组
        childLoopGroup.shutdownGracefully();
    }

    @Override
    public void run(String... strings) throws Exception {
        //设置Accept线程池和Read、Write线程池
        serverBootstrap.group(parentLoopGroup,childLoopGroup);
        //设置服务器渠道类型
        serverBootstrap.channel(NioServerSocketChannel.class);
        //TCP内核维护队列最大值，超过最大值新连接将会被TCP内核拒绝
        serverBootstrap.option(ChannelOption.SO_BACKLOG,100);
        //设置日志水平
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        try{
            //处理客户端请求过来的IO
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //获取到渠道中的管道
                    ChannelPipeline channelPipeline=socketChannel.pipeline();
                    //自定义长度解码器，阀
                    channelPipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024,0,2,0,2));
                    //编码器，它可以计算当前待发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头
                    channelPipeline.addLast(new LengthFieldPrepender(2));
                    //消息内容反序列化
                    channelPipeline.addLast(new ObjectCodec());
                    //服务器适配器
                    channelPipeline.addLast(adapter);
                }
            });
            //绑定服务端口
            log.info("netty服务器在[{}]端口启动监听", 2020);
            ChannelFuture channelFuture = serverBootstrap.bind(2020).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception ex){
            log.info("[出现异常] 释放资源");
            //异常，关闭parentLoopGroup线程组
            parentLoopGroup.shutdownGracefully();
            //异常，关闭childLoopGroup线程组
            childLoopGroup.shutdownGracefully();
        }
    }
}
