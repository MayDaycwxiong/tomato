package com.learning.tomato.dto.friends;


import java.io.Serializable;

/**
* @Description:    ServerAdd返回的DTO类
* @Author:         cuiwx
* @CreateDate:     2019/5/13 15:17
* @UpdateUser:     cuiwx
* @UpdateDate:     2019/5/13 15:17
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class ServerAddDTO extends ServerAddPO implements Serializable {
    /**
     * 包装的PO类
     */
    private ServerAddPO serverAddPO;
    /**
     * 返回标志
     * 0 成功获取
     * 1 失败获取
     */
    private String flage;

    public ServerAddPO getServerAddPO() {
        return serverAddPO;
    }

    public void setServerAddPO(ServerAddPO serverAddPO) {
        this.serverAddPO = serverAddPO;
    }

    public String getFlage() {
        return flage;
    }

    public void setFlage(String flage) {
        this.flage = flage;
    }

    @Override
    public String toString() {
        return "ServerAddDTO{" +
                "serverAddPO=" + serverAddPO.toString() +
                ", flage='" + flage + '\'' +
                '}';
    }
}
