package com.learning.tomato.until;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 定义属性文件
 * @date 2019/5/9 13:56
 */

public class MyStaticResource {
    /**
     * 线程池
     */
    public static ExecutorService MTHREADPOOL= Executors.newCachedThreadPool();
    /**
     * Android 手机端通讯端口
     */
    public static final int ANDROIDSERVERPORT=53822;
    /**
     * 字符串定义
     */
    public static String EMPTY="";
    /**
     * 用户信息定义
     */
    public static String USERNAME=null;
    public static String USERICON=null;
    public static String USERID=null;

    public static String serverIp=null;
    public static String clientSelfIp=null;

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
//        public static final String REQUESTIP="http://47.97.212.242";
//        public static final int REQUESTPORT=53822;
//        public static final String PROJECTNAME="websys-web";
//        public static final String REQUESTPREFIX=REQUESTIP+":"+REQUESTPORT+"/"+PROJECTNAME;
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
     * 好友模块接口定义 friends
     */
    // 接口信息，获取服务器ip以及端口信息接口url
    public static final String SERVERINFOINTERFACE="/friends/getServerInfo.pub";
    // 接口信息，获取好友IP地址信息接口url
    public static final String FRIENDIPADDRINTERFACE="/friends/getFriendIpAddr.pub";
    // 接口信息，添加好友接口url
    public static final String ADDFRIENDINTERFACE="/friends/addFriend.pub";
    // 接口信息，初始化好友分组接口url
    public static final String INITFRIENDGROUPINTERFACE="/friends/initFriendGroupInfo.pub";
    // 接口信息，获取好友列表接口url
    public static final String GETFRIENDSLISTINTERFACE="/friends/getFriendsList.pub";

    public static final String SERVERINFO=REQUESTPREFIX+SERVERINFOINTERFACE;
    public static final String FRIENDIPADDR=REQUESTPREFIX+FRIENDIPADDRINTERFACE;
    public static final String ADDFRIEND=REQUESTPREFIX+ADDFRIENDINTERFACE;
    public static final String INITFRIENDGROUP=REQUESTPREFIX+INITFRIENDGROUPINTERFACE;
    public static final String GETFRIENDSLIST=REQUESTPREFIX+GETFRIENDSLISTINTERFACE;
    /**
     * 分享中心模块接口定义 sharrigns
     */
    // 接口信息，用户添加动态接口url
    public static final String ADDACTIVITYINTERFACE="/sharings/addActivity.pub";
    // 接口信息，用户获取好友动态接口url
    public static final String GETFRIENDSACTIVITIESINTERFACE="/sharings/getFriendsActivities.pub";

    public static final String ADDACTIVITY=REQUESTPREFIX+ADDACTIVITYINTERFACE;
    public static final String GETFRIENDSACTIVITIES=REQUESTPREFIX+GETFRIENDSACTIVITIESINTERFACE;
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
