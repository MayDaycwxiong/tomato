package com.learning.tomato.dto.friends;


import java.io.Serializable;

/**
 * tb_serveradd
 */
public class ServerAddPO implements Serializable {
    /**
     * 服务器ip
     * serverIp
     */
    private String serverip;

    /**
     * 服务器端口
     * serverPort
     */
    private Integer serverport;

    /**
     * @return the value of tb_serveradd.serverIp
     */
    public String getServerip() {
        return serverip;
    }

    /**
     * @param serverip the value for tb_serveradd.serverIp
     */
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    /**
     * @return the value of tb_serveradd.serverPort
     */
    public Integer getServerport() {
        return serverport;
    }

    /**
     * @param serverport the value for tb_serveradd.serverPort
     */
    public void setServerport(Integer serverport) {
        this.serverport = serverport;
    }

    @Override
    public String toString() {
        return "ServerAddPO{" +
                "serverip='" + serverip + '\'' +
                ", serverport=" + serverport +
                '}';
    }
}