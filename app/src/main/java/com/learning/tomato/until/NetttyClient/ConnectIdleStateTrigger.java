package com.learning.tomato.until.NetttyClient;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 读写空闲
 * @date 2019/5/13 22:15
 */
@ChannelHandler.Sharable
public class ConnectIdleStateTrigger extends SimpleChannelInboundHandler<String> {
    private static final ByteBuf HEARTBATE= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBATE", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state=((IdleStateEvent) evt).state();
            if(state==IdleState.WRITER_IDLE){
                ctx.writeAndFlush(HEARTBATE.duplicate());
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }
}
