package com.learning.tomato.service.NettyServer;

import io.netty.channel.Channel;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: handler 接口
 * @date 2019/5/15 22:06
 */

public interface ClientHandler {
    void createChannel(String serverIp,int port,String userid);
    /**
     * 添加Channel
     * @param channel
     */
    void put(String userid,Channel channel);

    /**
     * 移除Channel
     * @param userid
     */
    void remove(String userid);

    /**
     * 发送数据
     */
    void writeData(String userid,String json);
}
