package com.learning.tomato.dto.friends;


import java.io.Serializable;

/**
 * @Description: 对FriendsOfUserGroupPO类进行封装
 * @Author: cuiwx
 * @CreateDate: 2019/5/17 12:51
 * @UpdateUser: cuiwx
 * @UpdateDate: 2019/5/17 12:51
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class FriendsOfUserGroupDTO implements Serializable {
    private FriendsOfUserGroupPO friendsOfUserGroupPO;
    /**
     * 操作成功标识
     * 0 操作成功
     * 1 操作失败
     */
    private String flag;

    public FriendsOfUserGroupPO getFriendsOfUserGroupPO() {
        return friendsOfUserGroupPO;
    }

    public void setFriendsOfUserGroupPO(FriendsOfUserGroupPO friendsOfUserGroupPO) {
        this.friendsOfUserGroupPO = friendsOfUserGroupPO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FriendsOfUserGroupDTO{" +
                "friendsOfUserGroupPO=" + friendsOfUserGroupPO.toString() +
                ", flag='" + flag + '\'' +
                '}';
    }
}
