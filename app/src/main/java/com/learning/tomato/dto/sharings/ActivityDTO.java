package com.learning.tomato.dto.sharings;


import java.io.Serializable;

/**
* @Description:    对ActivityPO类进行封装
* @Author:         cuiwx
* @CreateDate:     2019/5/18 10:48
* @UpdateUser:     cuiwx
* @UpdateDate:     2019/5/18 10:48
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class ActivityDTO implements Serializable {
    private ActivityPO activityPO;
    /**
     * 成功标识
     * 0 成功
     * 1 失败
     */
    private String flag;

    public ActivityPO getActivityPO() {
        return activityPO;
    }

    public void setActivityPO(ActivityPO activityPO) {
        this.activityPO = activityPO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
                "activityPO=" + activityPO +
                ", flag='" + flag + '\'' +
                '}';
    }
}
