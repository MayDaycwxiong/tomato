package com.learning.tomato.service.NetttyClient;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.dto.friends.IptablesDTO;
import com.learning.tomato.dto.friends.IptablesPO;
import com.learning.tomato.service.NettyServer.SimpleServer;
import com.learning.tomato.until.MyStaticResource;

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
    private static final String TAG = "ConnectIdleStateTrigger";
    public static IptablesPO iptablesPO=null;
    private IptablesDTO iptablesDTO=new IptablesDTO();
    private static ByteBuf HEARTBATE= null;
    public static boolean first=true;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state=((IdleStateEvent) evt).state();
            if(state==IdleState.WRITER_IDLE){
                iptablesDTO.setIptablesPO(iptablesPO);
                iptablesDTO.setHeartBate("HEARTBATE");
                if(first){
                    SimpleServer.getSimpleServerInstance(MyStaticResource.ANDROIDSERVERPORT);
                    iptablesDTO.setFirst("0");
                    String json=JSON.toJSONString(iptablesDTO);
                    Log.e(TAG,"心跳数据"+json);
                    HEARTBATE=Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(json, CharsetUtil.UTF_8));
                    first=false;
                    ctx.writeAndFlush(HEARTBATE.duplicate());
                }else {
                    iptablesDTO.setFirst("1");
                    String json=JSON.toJSONString(iptablesDTO);
                    Log.e(TAG,"心跳数据"+json);
                    HEARTBATE=Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(json, CharsetUtil.UTF_8));
                    ctx.writeAndFlush(HEARTBATE.duplicate());
                }
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }
}
