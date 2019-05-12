package com.learning.tomato.dto;


import java.io.Serializable;

/**
* @Description:    UserPO返回的DTO类
* @Author:         cuiwx
* @CreateDate:     2019/5/8 19:41
* @UpdateUser:     cuiwx
* @UpdateDate:     2019/5/8 19:41
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class UserDTO implements Serializable {
    /**
     * 包装的userPO类
     */
    private UserPO userPO;
    /**
     * 验证标志
     * 0 请求成功
     * 1 请求失败
     */
    private String flag;

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userPO=" + userPO +
                ", flag='" + flag + '\'' +
                '}';
    }
}
