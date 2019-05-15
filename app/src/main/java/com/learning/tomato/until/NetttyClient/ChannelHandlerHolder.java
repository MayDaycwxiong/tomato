package com.learning.tomato.until.NetttyClient;

import io.netty.channel.ChannelHandler;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 管理Channel
 * @date 2019/5/13 21:45
 */

public interface ChannelHandlerHolder {
    ChannelHandler[] handlers();
}
