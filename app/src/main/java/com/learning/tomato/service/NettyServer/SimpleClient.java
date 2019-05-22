package com.learning.tomato.service.NettyServer;


import android.util.Log;

import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: Android 通讯端
 * @date 2019/5/15 20:48
 */

public class SimpleClient {
    private static final String TAG = "ClientHandlerImpl";
    private static Bootstrap bootstrap=null;

    public static Bootstrap getBootstrap(){
        if(bootstrap==null){
            synchronized (SimpleClient.class){
                if(bootstrap==null){
                    EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
                    bootstrap=new Bootstrap()
                            .group(eventLoopGroup)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline=ch.pipeline();
                                    //pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                                    pipeline.addLast("decoder",new StringDecoder(Charset.forName("UTF-8")));
                                    pipeline.addLast("encoder",new StringEncoder(Charset.forName("UTF-8")));
                                    pipeline.addLast("handler", new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                            Log.e(TAG,"服务器返回消息"+msg);
                                            if(msg.equals("RECEIVED")){
                                                Log.e(TAG,"目标手机成功接收聊天信息");
                                            }
                                        }
                                    });
                                }
                            });
                }
            }
        }
        return bootstrap;
    }

}
