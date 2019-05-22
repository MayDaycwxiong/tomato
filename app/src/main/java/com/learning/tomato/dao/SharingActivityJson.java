package com.learning.tomato.dao;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2019/5/19 12:26
 */

public class SharingActivityJson {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String usericon;
    /**
     * 用户动态内容
     */
    private String usercontent;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getUsercontent() {
        return usercontent;
    }

    public void setUsercontent(String usercontent) {
        this.usercontent = usercontent;
    }

    @Override
    public String toString() {
        return "SharingActivityJson{" +
                "username='" + username + '\'' +
                ", usericon='" + usericon + '\'' +
                ", usercontent='" + usercontent + '\'' +
                '}';
    }
}
