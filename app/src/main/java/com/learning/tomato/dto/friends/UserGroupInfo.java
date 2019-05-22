package com.learning.tomato.dto.friends;

import java.io.Serializable;
import java.util.List;

/**
* @Description:    好友列表数组，
* @Author:         cuiwx
* @CreateDate:     2019/5/17 15:40
* @UpdateUser:     cuiwx
* @UpdateDate:     2019/5/17 15:40
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class UserGroupInfo implements Serializable {
    /**
     * 用户分组信息
     */
    private UserGroupPO userGroupPO;
    /**
     * 分组下的好友列表信息
     */
    private List<FriendInfo> friendInfoList;

    public UserGroupPO getUserGroupPO() {
        return userGroupPO;
    }

    public void setUserGroupPO(UserGroupPO userGroupPO) {
        this.userGroupPO = userGroupPO;
    }

    public List<FriendInfo> getFriendInfoList() {
        return friendInfoList;
    }

    public void setFriendInfoList(List<FriendInfo> friendInfoList) {
        this.friendInfoList = friendInfoList;
    }

    @Override
    public String toString() {
        return "UserGroupInfo{" +
                "userGroupPO=" + userGroupPO.toString() +
                ", friendInfoList=" + friendInfoList +
                '}';
    }
}
