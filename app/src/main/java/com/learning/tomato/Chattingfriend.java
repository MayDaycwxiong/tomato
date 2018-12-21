package com.learning.tomato;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 消息栏的实例，最近聊天好友列表
 * @date 2018/12/21 13:45
 */

public class Chattingfriend {
    /**
     * 图片(头像)
     */
    private int imageId;
    /**
     * 用户名 or 备注
     */
    private String name;
    /**
     * 收到 or 发送消息的时间
     */
    private String time;
    /**
     * 最近一条消息内容
     */
    private String message;

    public Chattingfriend(int imageId, String name, String time, String message) {
        this.imageId = imageId;
        this.name = name;
        this.time = time;
        this.message = message;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Chattingfriend{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
