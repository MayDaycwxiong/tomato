package com.learning.tomato.entity;

import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 好友分组
 * @date 2018/12/25 22:27
 */

public class FriendsGroup {
    /**
     * 分组标题
     */
    private String TitleName;
    /**
     * 该分组下的好友列表
     */
    private List<Friend> FriendsList;

    public String getTitleName() {
        return TitleName;
    }

    public void setTitleName(String titleName) {
        TitleName = titleName;
    }

    public List<Friend> getFriendsList() {
        return FriendsList;
    }

    public void setFriendsList(List<Friend> friendsList) {
        FriendsList = friendsList;
    }
}
