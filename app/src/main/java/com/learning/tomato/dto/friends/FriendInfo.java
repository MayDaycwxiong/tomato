package com.learning.tomato.dto.friends;

import com.learning.tomato.dto.users.UserPO;

import java.io.Serializable;

/**
 * @Description: 对分组下的好友信息(分组表中friend - > 用户表中的记录)进行封装
 * @Author: cuiwx
 * @CreateDate: 2019/5/17 16:00
 * @UpdateUser: cuiwx
 * @UpdateDate: 2019/5/17 16:00
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class FriendInfo implements Serializable {
    /**
     * 好友信息
     */
    private UserPO userPO;
    /**
     * 好友账号(userid)
     */
    private String friendId;

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "FriendInfo{" +
                "userPO=" + userPO.toString() +
                ", friendId='" + friendId + '\'' +
                '}';
    }
}
