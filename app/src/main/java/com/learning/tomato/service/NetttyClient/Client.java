package com.learning.tomato.service.NetttyClient;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: Netty客户端
 * @date 2019/5/13 21:24
 */

public class Client {
    public static Client client=null;
    private static final String TAG = "Simple";
    private String serverIp;
    private int serverPort;
    public Channel channel;
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private final HashedWheelTimer timer=new HashedWheelTimer();
    private ConnectIdleStateTrigger idleStateTrigger=new ConnectIdleStateTrigger();

    private Client(String host,int port){
        this.serverIp=host;
        this.serverPort=port;
        startClient();
    }
    public static Client getInstance(String host,int port){
        if(client==null){
            synchronized (Client.class){
                if(client==null){
                    client=new Client(host,port);
                }
            }
        }
        return client;
    }

    private void startClient() {
        group=new NioEventLoopGroup();
        bootstrap=new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));
        final ConnectionWatchDog watchDog=new ConnectionWatchDog(bootstrap,timer,serverPort,serverIp,true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        new IdleStateHandler(0,10,0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()),
                        new StringDecoder(Charset.forName("UTF-8")),
                        new StringEncoder(Charset.forName("UTF-8")),
                        new ClientHandler()
                };
            }

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

            }
        };
        ChannelFuture future;
        synchronized (bootstrap){
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(watchDog.handlers());
                }
            });
            future=bootstrap.connect(serverIp,serverPort);
            Log.e(TAG,"连接服务器成功");
        }
        try {
            channel=future.sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG,"连接异常");
        }
    }

    /**
     * 暴露给客户端调用的接口
     * @param str
     */
    public void clientSendMessge(String str){
        if(ConnectionWatchDog.channel!=null){
            channel=ConnectionWatchDog.channel;
        }
        channel.writeAndFlush(str+"\r\n");
    }

    /**
     * 资源关闭
     */
    public void closeResource(){
        group.shutdownGracefully();
    }
}
