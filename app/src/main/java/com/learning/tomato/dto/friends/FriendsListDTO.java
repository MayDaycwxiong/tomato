package com.learning.tomato.dto.friends;


import java.io.Serializable;
import java.util.List;

/**
 * @Description: 对好友分组信息UserGroupInfo进行封装
 * @Author: cuiwx
 * @CreateDate: 2019/5/17 17:04
 * @UpdateUser: cuiwx
 * @UpdateDate: 2019/5/17 17:04
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class FriendsListDTO implements Serializable {
    /**
     * 好友分组信息
     */
    private List<UserGroupInfo> userGroupInfoList;
    /**
     * 获取分组信息标识
     * 0 成功
     * 1 失败
     */
    private String flag;

    public List<UserGroupInfo> getUserGroupInfoList() {
        return userGroupInfoList;
    }

    public void setUserGroupInfoList(List<UserGroupInfo> userGroupInfoList) {
        this.userGroupInfoList = userGroupInfoList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FriendsListDTO{" +
                "userGroupInfoList=" + userGroupInfoList +
                ", flag='" + flag + '\'' +
                '}';
    }
}
