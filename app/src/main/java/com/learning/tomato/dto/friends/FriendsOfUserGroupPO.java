package com.learning.tomato.dto.friends;


import java.io.Serializable;

/**
 * tb_friendsofusergroup
 */
public class FriendsOfUserGroupPO implements Serializable {
    /**
     * 用户id，即用户账号，对应user表的userId
     * userId
     */
    private String userid;

    /**
     * 好友id，即用户账号，对应user表的userId
     * friend
     */
    private String friend;

    /**
     * 分组号，对应usergroup 中的userGroupId
     * userGroupId
     */
    private Integer usergroupid;

    /**
     * 朋友标识，0为互为好友，1为曾经互为好友，删除好友时把该标识置为1
     * friendFlag
     */
    private String friendflag;

    /**
     * @return the value of tb_friendsofusergroup.userId
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the value for tb_friendsofusergroup.userId
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the value of tb_friendsofusergroup.friend
     */
    public String getFriend() {
        return friend;
    }

    /**
     * @param friend the value for tb_friendsofusergroup.friend
     */
    public void setFriend(String friend) {
        this.friend = friend;
    }

    /**
     * @return the value of tb_friendsofusergroup.userGroupId
     */
    public Integer getUsergroupid() {
        return usergroupid;
    }

    /**
     * @param usergroupid the value for tb_friendsofusergroup.userGroupId
     */
    public void setUsergroupid(Integer usergroupid) {
        this.usergroupid = usergroupid;
    }

    /**
     * @return the value of tb_friendsofusergroup.friendFlag
     */
    public String getFriendflag() {
        return friendflag;
    }

    /**
     * @param friendflag the value for tb_friendsofusergroup.friendFlag
     */
    public void setFriendflag(String friendflag) {
        this.friendflag = friendflag;
    }

    @Override
    public String toString() {
        return "FriendsOfUserGroupPO{" +
                "userid='" + userid + '\'' +
                ", friend='" + friend + '\'' +
                ", usergroupid=" + usergroupid +
                ", friendflag='" + friendflag + '\'' +
                '}';
    }
}