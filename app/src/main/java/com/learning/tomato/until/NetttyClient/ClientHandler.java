package com.learning.tomato.until.NetttyClient;

import android.util.Log;

import java.util.Date;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 处理器类
 * @date 2019/5/13 22:23
 */
@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private static final String TAG = "Simple";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"激活时间是："+new Date());
        Log.e(TAG,"HEARTBATECLIENTHANDLER channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"停止时间是："+new Date());
        Log.e(TAG,"HEARTBATECLIENTHANDLER channelInactive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if(msg.equals("HEARTBATE")){
            ctx.writeAndFlush("READ HEARTBATE FROM SERVER");
        }else{
            Log.e(TAG,"接收到的消息是："+msg);
        }
    }
}
