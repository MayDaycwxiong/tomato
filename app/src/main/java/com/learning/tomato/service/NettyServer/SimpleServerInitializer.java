package com.learning.tomato.service.NettyServer;

import android.util.Log;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 初始化绑定管道
 * @date 2019/5/15 18:17
 */

class SimpleServerInitializer extends ChannelInitializer<SocketChannel>{

    private static final String TAG = "SimpleServer";
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();

        //pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder",new StringDecoder(Charset.forName("UTF-8")));
        pipeline.addLast("encoder",new StringEncoder(Charset.forName("UTF-8")));

        pipeline.addLast("handler",new SimpleServerHandler());
        InetSocketAddress in=ch.remoteAddress();
        String ip=in.getAddress().getHostAddress();
        Log.e(TAG,"客户端:"+ip+"连接上");
    }
}
