package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.dto.friends.IptablesPO;
import com.learning.tomato.dto.friends.ServerAddDTO;
import com.learning.tomato.dto.friends.ServerAddPO;
import com.learning.tomato.dto.users.UserDTO;
import com.learning.tomato.dto.users.UserPO;
import com.learning.tomato.service.NetttyClient.Simple;
import com.learning.tomato.service.NettyServer.SimpleServer;
import com.learning.tomato.until.ActivityCollector;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.service.NetttyClient.ConnectIdleStateTrigger;
import com.learning.tomato.until.ip.IpAddress;
import com.learning.tomato.service.netUtil.OkManager;
import com.learning.tomato.until.paramUtil.ObjectUtil;
import com.learning.tomato.until.paramUtil.StringUtil;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private OkManager okManager=null;
    private String url= MyStaticResource.LOGINURL;
    Map<String,String> map=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG,"onCreate execute");
        Button loginButton=findViewById(R.id.login);
        TextView register=findViewById(R.id.register);
        final EditText userIdText=findViewById(R.id.login_userid);
        final EditText passwordText=findViewById(R.id.password);

        Intent intent=getIntent();
        if(StringUtil.isNotEmpty(intent.getStringExtra("userid"))){
            userIdText.setText(intent.getStringExtra("userid"));
        }
        if(StringUtil.isNotEmpty(intent.getStringExtra("userpassword"))){
            passwordText.setText(intent.getStringExtra("userpassword"));
        }
        okManager=OkManager.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OkHttpClient client=new OkHttpClient();
//                RequestBody requestBody=new FormBody.Builder()
//                        .add("userid","15243643896")
//                        .build();
//                Request request=new Request.Builder()
//                        .url("http://192.168.137.1:8080/websys_web_war_exploded/users/login.pub")
//                        .post(requestBody)
//                        .build();
//                Call call=client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e(TAG,"异常消息："+e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String res=response.body().string();
//                        Log.e(TAG,"响应消息:"+res);
//                        UserPO userPO=JSON.parseObject(res,UserPO.class);
//                        Log.e(TAG,"响应消息:"+userPO);
//                        Log.e(TAG,"响应消息:"+userPO.getUserid());
//                    }
//                });
                if(!userIdText.getText().toString().equals("")&&!passwordText.getText().toString().equals("")){
                    Log.e(TAG,"账号："+userIdText.getText().toString());
                    Log.e(TAG,"密码："+passwordText.getText().toString());
                    Log.e(TAG,"请求url:"+url);
                    map=new HashMap<>();
                    map.put("userid",userIdText.getText().toString());
                    map.put("userpassword",passwordText.getText().toString());
                    okManager.asynJsonObjectByRequest(url, map, new OkManager.Func1() {
                        @Override
                        public void onResponse(String result) {
                            Log.e(TAG,result);
                            UserDTO userDTO= JSON.parseObject(result,UserDTO.class);
                            Log.e(TAG,"响应消息:"+userDTO);
                            Log.e(TAG,"响应标识:"+userDTO.getFlag());
                            resultMapping(userDTO.getFlag(),userDTO.getUserPO());
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
//                    MainActivity.actionStart(LoginActivity.this);
//                    MyStaticResource.MTHREADPOOL.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            IpAddress ipAddress=new IpAddress();
//                            String ip=ipAddress.getLocalhostIp(LoginActivity.this);
//                            if(StringUtil.isNotEmpty(ip)){
//                                Log.e(TAG,"当前ip为"+ip);
//                                IptablesPO iptablesPO=new IptablesPO();
//                                iptablesPO.setClientselfip(ip);
//                                iptablesPO.setUserid("15243643896");
//                                ConnectIdleStateTrigger.iptablesPO=iptablesPO;
//                                ChattingActivity.clientSelfIp=ip;
//                            }
//                        }
//                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"点击register");
                RegisterActivity.actionStart(LoginActivity.this);
            }
        });
    }

    /**
     * 返回结果映射处理
     * @param flag
     * @param userPO
     */
    private void resultMapping(String flag, UserPO userPO) {
        switch (flag){
            case "0":
                MyStaticResource.USERID=userPO.getUserid();
                MyStaticResource.USERICON=userPO.getUsericon();
                MyStaticResource.USERNAME=userPO.getUsername();
                Log.e(TAG,"静态资源");
                Log.e(TAG,"userid="+MyStaticResource.USERID+"usericon="+MyStaticResource.USERICON+"uername="+MyStaticResource.USERNAME);
                // 向服务器注册自己的IP地址时使用
                getLocalhostIp(userPO.getUserid());
                // 获取服务器IP地址与端口，通讯使用
                getServerAddr();
//                SimpleServer.getSimpleServerInstance(MyStaticResource.ANDROIDSERVERPORT);
                ActivityCollector.finishAll();
                MainActivity.actionStart(LoginActivity.this);
                break;
            case "1":
                Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                break;
            case "-1":
                Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 得到服务器IP地址以及端口信息
     */
    private void getServerAddr() {
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                okManager.asynJsonObjectByRequest(MyStaticResource.SERVERINFO, null, new OkManager.Func1() {
                    @Override
                    public void onResponse(String result) {
                        Log.e(TAG,"获取服务器地址："+result);
                        ServerAddDTO serverAddDTO = JSON.parseObject(result, ServerAddDTO.class);
                        Log.e(TAG, "响应消息:" + serverAddDTO);
                        Log.e(TAG, "响应标识:" + serverAddDTO.getFlage());
                        resultMapping(serverAddDTO);
                    }
                });
            }
        });
    }

    /**
     * 响应消息映射
     * @param serverAddDTO
     */
    private void resultMapping(ServerAddDTO serverAddDTO) {
        if(serverAddDTO.getFlage().equals("0")){
            ServerAddPO serverAddPO=serverAddDTO.getServerAddPO();
            if(ObjectUtil.isNotNull(serverAddPO)){
                Simple.serverIP=StringUtil.isNotEmpty(serverAddPO.getServerip())?serverAddPO.getServerip():MyStaticResource.EMPTY;
                if(StringUtil.isNotEmpty(Simple.serverIP)){
                    ChattingActivity.serverIp=Simple.serverIP;
                    Log.e(TAG,"ChattingActivity 中的ServerIP="+ChattingActivity.serverIp);
                }
                Simple.port=ObjectUtil.isNotNull(serverAddPO.getServerport())?serverAddPO.getServerport():0;
                Log.e(TAG,"获取的服务器IP="+Simple.serverIP+",服务器的端口port="+Simple.port);
                new Simple().start();
            }
        }else if(serverAddDTO.getFlage().equals("1")){
            Log.e(TAG,"当前没有可用的服务器");
        }else{
            Log.e(TAG,"未知返回消息");
        }

    }

    /**
     * 得到手机端IP地址
     * @param userid
     */
    private void getLocalhostIp(final String userid) {
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                IpAddress ipAddress=new IpAddress();
                String ip=ipAddress.getLocalhostIp(LoginActivity.this);
                if(StringUtil.isNotEmpty(ip)){
                    Log.e(TAG,"当前ip为"+ip);
                    IptablesPO iptablesPO=new IptablesPO();
                    iptablesPO.setClientselfip(ip);
                    iptablesPO.setUserid(userid);
                    ConnectIdleStateTrigger.iptablesPO=iptablesPO;
                    ChattingActivity.clientSelfIp=ip;
                    Log.e(TAG,"ChattingActivity 中的ClientSelfIP="+ChattingActivity.clientSelfIp);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(map!=null){
            map.clear();
            map=null;
        }
    }

    public static void actionStart(Context context,String userid,String userpassword){
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra("userid",userid);
        intent.putExtra("userpassword",userpassword);
        context.startActivity(intent);
    }
}
