package com.learning.tomato.service.NetttyClient;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 断线重连机制
 * @date 2019/5/13 21:43
 */
@ChannelHandler.Sharable
public abstract class ConnectionWatchDog extends SimpleChannelInboundHandler<String> implements TimerTask,ChannelHandlerHolder {
    private static final String TAG = "Simple";
    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int serverPort;
    private final String serverIp;
    private volatile boolean reconnect=true;
    private int attempts;

    public static Channel channel=null;

    public ConnectionWatchDog(Bootstrap bootstrap,Timer timer,int serverPort,String serverIp,boolean reconnect){
        this.bootstrap=bootstrap;
        this.timer=timer;
        this.serverPort=serverPort;
        this.serverIp=serverIp;
        this.reconnect=reconnect;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"当前链路激活，重连尝试次数置为0");
        attempts=0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"链路关闭");
        if(reconnect){
            Log.e(TAG,"链路关闭，将进行重连");
            attempts++;
            int timeout=6<<attempts;
            timer.newTimeout(this,timeout, TimeUnit.MILLISECONDS);
        }
        ctx.fireChannelInactive();
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture channelFuture;
        synchronized (bootstrap){
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(handlers());
                }
            });
            channelFuture=bootstrap.connect(serverIp,serverPort);
        }
        channel=channelFuture.channel();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                boolean success=future.isSuccess();
                if(!success&&attempts<6){
                    Log.e(TAG,"重连失败");
                    future.channel().pipeline().fireChannelInactive();
                }else if(success){
                    Log.e(TAG,"重连成功");
                    ConnectIdleStateTrigger.first=true;
                }else{
                    Log.e(TAG,"重连6次后，重连服务器失败");
                }
            }
        });
    }
}
