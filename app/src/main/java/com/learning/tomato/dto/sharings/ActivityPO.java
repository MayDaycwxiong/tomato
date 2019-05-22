package com.learning.tomato.dto.sharings;


import java.io.Serializable;
import java.util.Date;

/**
 * tb_activity
 */
public class ActivityPO implements Serializable {
    /**
     * 动态id，自增长，唯一标识一条动态
     * activityId
     */
    private Integer activityid;

    /**
     * 动态内容，包含文字内容和图片内容，为一个json字符串
     * contentJson
     */
    private String contentjson;

    /**
     * 服务器接收到此条消息的时间
     * receiveTime
     */
    private Date receivetime;

    /**
     * 用户id,与tb_user表中的userId对应
     * userId
     */
    private String userid;

    /**
     * @return the value of tb_activity.activityId
     */
    public Integer getActivityid() {
        return activityid;
    }

    /**
     * @param activityid the value for tb_activity.activityId
     */
    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    /**
     * @return the value of tb_activity.contentJson
     */
    public String getContentjson() {
        return contentjson;
    }

    /**
     * @param contentjson the value for tb_activity.contentJson
     */
    public void setContentjson(String contentjson) {
        this.contentjson = contentjson;
    }

    /**
     * @return the value of tb_activity.receiveTime
     */
    public Date getReceivetime() {
        return receivetime;
    }

    /**
     * @param receivetime the value for tb_activity.receiveTime
     */
    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }

    /**
     * @return the value of tb_activity.userId
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the value for tb_activity.userId
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ActivityPO{" +
                "activityid=" + activityid +
                ", contentjson='" + contentjson + '\'' +
                ", receivetime=" + receivetime +
                ", userid='" + userid + '\'' +
                '}';
    }
}