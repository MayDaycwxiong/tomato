package com.learning.tomato.dto.friends;

import java.io.Serializable;

/**
 * @Description: 对IptablesPO类进行封装,用于Netty服务器
 * @Author: cuiwx
 * @CreateDate: 2019/5/15 13:29
 * @UpdateUser: cuiwx
 * @UpdateDate: 2019/5/15 13:29
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class IptablesDTO implements Serializable {
    private IptablesPO iptablesPO;
    /**
     * 注册ip标识
     * 0 为未注册
     * 1为已注册
     */
    private String first;
    /**
     * 心跳包
     */
    private String heartBate;

    public IptablesPO getIptablesPO() {
        return iptablesPO;
    }

    public void setIptablesPO(IptablesPO iptablesPO) {
        this.iptablesPO = iptablesPO;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getHeartBate() {
        return heartBate;
    }

    public void setHeartBate(String heartBate) {
        this.heartBate = heartBate;
    }

    @Override
    public String toString() {
        return "IptablesDTO{" +
                "iptablesPO=" + iptablesPO.toString() +
                ", first='" + first + '\'' +
                ", heartBate='" + heartBate + '\'' +
                '}';
    }
}
