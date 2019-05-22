package com.learning.tomato.service.NetttyClient;

import android.util.Log;

import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.until.paramUtil.StringUtil;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 统一接口
 * @date 2019/5/13 22:40
 */

public class Simple {
    private static final String TAG = "Simple";
    public static String serverIP;
    public static int port;
    public void start(){
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"连接服务器");
                if(StringUtil.isNotEmpty(serverIP)&&port>1024){
                    Client.getInstance(serverIP,port);
                }
//                Client.getInstance("172.23.122.9",53822);
            }
        });
    }
}
