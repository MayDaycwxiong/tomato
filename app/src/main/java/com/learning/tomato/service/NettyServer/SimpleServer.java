package com.learning.tomato.service.NettyServer;

import android.util.Log;

import com.learning.tomato.service.NetttyClient.Simple;
import com.learning.tomato.until.MyStaticResource;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: netty 服务器启动类
 * @date 2019/5/15 16:38
 */

public class SimpleServer {
    private static SimpleServer simpleServer=null;
    private static int port;
    private static final String TAG = "SimpleServer";

    private SimpleServer(int port){
        this.port=port;
    }

    /**
     * 获取实例
     * @param port
     * @return
     */
    public static SimpleServer getSimpleServerInstance(int port){
        if(simpleServer==null){
            synchronized (SimpleServer.class){
                if(simpleServer==null){
                    simpleServer=new SimpleServer(port);
                    MyStaticResource.MTHREADPOOL.execute(new Runnable() {
                        @Override
                        public void run() {
                            start();
                        }
                    });
                }
            }
        }
        return simpleServer;
    }

    /**
     * 启动Android服务器
     */
    public static void start(){
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap()
                    .group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleServerInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
                ChannelFuture f=b.bind(port).sync();
                Log.e(TAG,"启动Android服务器");
                f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG,"启动Android服务器失败");
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            Log.e(TAG,"线程组关闭");
        }
    }
}
