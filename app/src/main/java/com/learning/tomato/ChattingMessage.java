package com.learning.tomato;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 消息实例
 * @date 2018/12/22 17:17
 */

public class ChattingMessage {

    public static final int TYPE_SENT = 0;

    public static final int TYPE_RECEIVED = 1;

    private String content;

    private int type;

    public ChattingMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
