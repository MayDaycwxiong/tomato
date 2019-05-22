package com.learning.tomato.dto.sharings;


import java.io.Serializable;
import java.util.List;

/**
* @Description:    获取好友动态(list)
* @Author:         cuiwx
* @CreateDate:     2019/5/18 11:51
* @UpdateUser:     cuiwx
* @UpdateDate:     2019/5/18 11:51
* @UpdateRemark:   修改内容
* @Version:        1.0
*/

public class GetActivitysDTO implements Serializable {
    private List<ActivityPO> activityPOList;
    /**
     * 获取成功标识
     * 0 成功
     * 1 失败
     */
    private String flag;

    public List<ActivityPO> getActivityPOList() {
        return activityPOList;
    }

    public void setActivityPOList(List<ActivityPO> activityPOList) {
        this.activityPOList = activityPOList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "GetActivitysDTO{" +
                "activityPOList=" + activityPOList +
                ", flag='" + flag + '\'' +
                '}';
    }
}
