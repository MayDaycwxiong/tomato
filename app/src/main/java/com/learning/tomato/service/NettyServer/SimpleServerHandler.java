package com.learning.tomato.service.NettyServer;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.controller.ChattingActivity;
import com.learning.tomato.dao.ReceiveMessage;
import com.learning.tomato.until.MyStaticResource;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 通道核心处理类
 * @date 2019/5/15 18:32
 */

class SimpleServerHandler extends SimpleChannelInboundHandler<String> {
    private static final String TAG = "SimpleServer";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        InetSocketAddress in= (InetSocketAddress) incoming.remoteAddress();
        String clientIp=in.getAddress().getHostAddress();
        Log.e(TAG,"客户端"+clientIp+"在线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming=ctx.channel();
        InetSocketAddress in= (InetSocketAddress) incoming.remoteAddress();
        String clientIp=in.getAddress().getHostAddress();
        Log.e(TAG,"收到客户端:"+clientIp+"消息:"+msg);
        ctx.channel().writeAndFlush("RECEIVED");
        sendMessage(msg);
    }

    /**
     * 分发收到的消息
     * @param msg
     */
    private void sendMessage(final String msg) {
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,msg);
                ReceiveMessage receiveMessage= JSON.parseObject(msg,ReceiveMessage.class);
                Log.e(TAG,"反序列化："+receiveMessage.toString());
                Message message=Message.obtain();
                message.what=0x001;
                // 发送给ChattingActivity
                Bundle data=new Bundle();
                data.putString("RECEIVED",receiveMessage.getMessage());
                message.setData(data);
                Log.e(TAG,"开始分发消息");
                ChattingActivity.handler.sendMessage(message);
                // 发送给FriendsListViewController
                /**
                 * 注意：
                 * 这里的data不可以清除，handler中携带的这个data可能是全局变量，如果改变该参数，那么对应的接收handler中的该值也会改变
                 */
//                data.clear();
//                data.putSerializable("RECEIVEMESSAGE",receiveMessage);
//                message.setData(data);

//                data=null;
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        InetSocketAddress in= (InetSocketAddress) incoming.remoteAddress();
        String clientIp=in.getAddress().getHostAddress();
        Log.e(TAG,"客户端"+clientIp+"加入");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        InetSocketAddress in= (InetSocketAddress) incoming.remoteAddress();
        String clientIp=in.getAddress().getHostAddress();
        Log.e(TAG,"客户端："+clientIp+"掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming=ctx.channel();
        InetSocketAddress in= (InetSocketAddress) incoming.remoteAddress();
        String clientIp=in.getAddress().getHostAddress();
        Log.e(TAG,"客户端"+clientIp+"异常");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        InetSocketAddress in= (InetSocketAddress) incoming.remoteAddress();
        String clientIp=in.getAddress().getHostAddress();
        Log.e(TAG,"客户端："+clientIp+"离开");
    }
}
