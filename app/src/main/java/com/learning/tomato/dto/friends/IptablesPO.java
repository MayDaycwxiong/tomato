package com.learning.tomato.dto.friends;


import java.io.Serializable;

/**
 * tb_iptables
 */
public class IptablesPO implements Serializable {
    /**
     * 服务器分配的通道id
     * channelId
     */
    private String channelid;

    /**
     * 服务器自身Ip
     * serverIp
     */
    private String serverip;

    /**
     * 服务器获取的客户端IP，为客户端所在网关Ip
     * clientIp
     */
    private String clientip;

    /**
     * 用户id
     * userid
     */
    private String userid;

    /**
     * 客户端携带的客户端自身Ip
     * clientSelfIp
     */
    private String clientselfip;

    /**
     * @return the value of tb_iptables.channelId
     */
    public String getChannelid() {
        return channelid;
    }

    /**
     * @param channelid the value for tb_iptables.channelId
     */
    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    /**
     * @return the value of tb_iptables.serverIp
     */
    public String getServerip() {
        return serverip;
    }

    /**
     * @param serverip the value for tb_iptables.serverIp
     */
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    /**
     * @return the value of tb_iptables.clientIp
     */
    public String getClientip() {
        return clientip;
    }

    /**
     * @param clientip the value for tb_iptables.clientIp
     */
    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    /**
     * @return the value of tb_iptables.userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the value for tb_iptables.userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the value of tb_iptables.clientSelfIp
     */
    public String getClientselfip() {
        return clientselfip;
    }

    /**
     * @param clientselfip the value for tb_iptables.clientSelfIp
     */
    public void setClientselfip(String clientselfip) {
        this.clientselfip = clientselfip;
    }

    @Override
    public String toString() {
        return "IptablesPO{" +
                "channelid='" + channelid + '\'' +
                ", serverip='" + serverip + '\'' +
                ", clientip='" + clientip + '\'' +
                ", userid='" + userid + '\'' +
                ", clientselfip='" + clientselfip + '\'' +
                '}';
    }
}