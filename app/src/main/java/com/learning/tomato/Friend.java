package com.learning.tomato;

import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 好友列表实体类
 * @date 2018/12/25 21:41
 */

public class Friend {
    /**
     * 好友头像
     */
    private int friendHeadIcon;
    /**
     * 好友昵称或者备注
     */
    private String friendName;
    /**
     * 在线状态与离线状态
     */
    private String state;
    /**
     * 座右铭
     */
    private String motto;


    public int getFriendHeadIcon() {
        return friendHeadIcon;
    }

    public void setFriendHeadIcon(int friendHeadIcon) {
        this.friendHeadIcon = friendHeadIcon;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }
}
