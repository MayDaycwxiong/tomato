package com.learning.tomato.until;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.RequestBody;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 线程池
 * @date 2019/5/9 13:56
 */

public class MyStaticResource {
    /**
     * 线程池
     */
    public static ExecutorService MTHREADPOOL= Executors.newCachedThreadPool();
    /**
     * 字符串定义
     */
    public static String EMPTY="";
    /**
     * test环境，请求url统一定义
     */
    public static final String REQUESTIP="http://172.23.122.9";
    public static final int REQUESTPORT=8080;
    public static final String PROJECTNAME="websys_web_war_exploded";
    public static final String REQUESTPREFIX=REQUESTIP+":"+REQUESTPORT+"/"+PROJECTNAME;
    /**
     * produce部署环境，请求url统一定义
   */
    //    public static final String REQUESTIP="http://172.23.122.9";
    //    public static final int REQUESTPORT=8080;
    //    public static final String PROJECTNAME="websys_web_war_exploded";
    //    public static final String REQUESTPREFIX=REQUESTIP+":"+REQUESTPORT+"/"+PROJECTNAME;
    /**
     * 用户模块接口定义 users
     */
    // 接口信息，登录接口url
    public static final String LOGININTERFACEURL="/users/login.pub";
    // 接口信息，用户基本信息接口url
    public static final String UPDATEBASEINFOINTERFACEURL="/users/updateBaseInfo.pub";
    // 接口信息，获取用户所有信息接口url
    public static final String SELECTALLUSERINFOINTERFACEURL="/users/selectAllUserInfo.pub";
    // 接口信息，申请账号与注册账号接口url
    public static final String REGISTERINTERFACEURL="/users/register.pub";

    public static final String LOGINURL=REQUESTPREFIX+LOGININTERFACEURL;
    public static final String UPDATEBASEINFOURL=REQUESTPREFIX+UPDATEBASEINFOINTERFACEURL;
    public static final String SELECTALLUSERINFOURL= REQUESTPREFIX+SELECTALLUSERINFOINTERFACEURL;
    public static final String REGISTERURL=REQUESTPREFIX+REGISTERINTERFACEURL;

    /**
     * fastDFS客户端统一变量定义
     */
    public static final String FDFSTRACKERURL="129.204.108.13";
    public static final int FDFSTRACKERPORT=22122;
    public static final int FDFSTRACKERHTTPPORT=8888;
    public static final int NETWORKTIMEOUT=30000;
    public static final int CONNECTTIMEOUT=2000;
    public static final String FDFSCHARSET="UTF-8";
    public static final String VISITPREFIX="http://"+FDFSTRACKERURL+":"+FDFSTRACKERHTTPPORT+"/";
}
