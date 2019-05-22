package com.learning.tomato.until.ip;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.learning.tomato.until.paramUtil.ObjectUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 获取本机ip地址
 * @date 2019/5/15 11:17
 */

public class IpAddress {
    /**
     * 得到本机ip地址
     * @param context
     * @return
     */
    public String getLocalhostIp(Context context){
        NetworkInfo networkInfo=((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(ObjectUtil.isNotNull(networkInfo)&&networkInfo.isConnected()){
            if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){     //非Wifi状态
                try {
                    for(Enumeration<NetworkInterface> enumeration=NetworkInterface.getNetworkInterfaces();enumeration.hasMoreElements();){
                        NetworkInterface networkInterface=enumeration.nextElement();
                        for(Enumeration<InetAddress> enumeIpAddress=networkInterface.getInetAddresses();enumeIpAddress.hasMoreElements();){
                            InetAddress inetAddress=enumeIpAddress.nextElement();
                            if(!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address){
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }else if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){     // wifi状态
                WifiManager wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo=wifiManager.getConnectionInfo();
                String ipAddress=trans2String(wifiInfo.getIpAddress());
                return ipAddress;
            }else{

            }
        }
        return null;
    }

    private String trans2String(int ipAddress) {
        return (ipAddress & 0xFF)+"."+((ipAddress>>8)&0xFF)+"."
                +((ipAddress>>16)&0xFF)+"."
                +((ipAddress>>24)&0xFF);
    }
}
