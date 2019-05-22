package com.learning.tomato.dto.friends;


import java.io.Serializable;

/**
* @Description:    对UserGroupPO类进行封装
* @Author:         cuiwx
* @CreateDate:     2019/5/17 2:20
* @UpdateUser:     cuiwx
* @UpdateDate:     2019/5/17 2:20
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class UserGroupDTO implements Serializable {
    private UserGroupPO userGroupPO;
    /**
     * 新建分组标识
     * 0 成功
     * 1 失败
     */
    private String flag;

    public UserGroupPO getUserGroupPO() {
        return userGroupPO;
    }

    public void setUserGroupPO(UserGroupPO userGroupPO) {
        this.userGroupPO = userGroupPO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "UserGroupDTO{" +
                "userGroupPO=" + userGroupPO.toString() +
                ", flag='" + flag + '\'' +
                '}';
    }
}
