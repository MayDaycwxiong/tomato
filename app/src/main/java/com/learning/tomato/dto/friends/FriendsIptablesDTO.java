package com.learning.tomato.dto.friends;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 对IptablesPO进行封装，用于Friends模块
 * @date 2019/5/17 0:53
 */

public class FriendsIptablesDTO {
    private IptablesPO iptablesPO;
    /**
     * 返回标识
     * 0 获取成功
     * 1 获取失败
     */
    private String flag;

    public IptablesPO getIptablesPO() {
        return iptablesPO;
    }

    public void setIptablesPO(IptablesPO iptablesPO) {
        this.iptablesPO = iptablesPO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FriendsIptablesDTO{" +
                "iptablesPO=" + iptablesPO.toString() +
                ", flag='" + flag + '\'' +
                '}';
    }
}
