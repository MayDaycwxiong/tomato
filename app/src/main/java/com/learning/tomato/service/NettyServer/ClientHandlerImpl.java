package com.learning.tomato.service.NettyServer;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 管理客户端
 * @date 2019/5/15 21:57
 */

public class ClientHandlerImpl implements ClientHandler{

    private static final String TAG = "ClientHandlerImpl";
    public static Map<String,Channel> channelMap=new HashMap<>();

    @Override
    public void createChannel(String serverIp, int port, String userid) {
        Bootstrap bootstrap=SimpleClient.getBootstrap();
        try {
            if(!channelMap.containsKey(userid)){
                Log.e(TAG,"准备连接目标手机");
                Channel channel=bootstrap.connect(serverIp,port).sync().channel();
                Log.e(TAG,"连接目标手机成功");
                put(userid,channel);
                Log.e(TAG,"用户添加到Map中成功");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG,"连接服务器异常");
        }
    }

    @Override
    public void put(String userid, Channel channel) {
        channelMap.put(userid,channel);
    }

    @Override
    public void remove(String userid) {
        if(channelMap.containsKey(userid)){
            Channel channel=channelMap.get(userid);
            channel.close().awaitUninterruptibly();
            channelMap.remove(userid);
        }
    }

    @Override
    public void writeData(String userid,String json) {
        if(channelMap.containsKey(userid)){
            Channel channel=channelMap.get(userid);
            Log.e(TAG,"向目标手机写数据");
            channel.writeAndFlush(json);
            Log.e(TAG,"向目标手机写数据完成");
            channelMap.remove(userid);
            Log.e(TAG,"移除会话通道");
        }
    }
}
