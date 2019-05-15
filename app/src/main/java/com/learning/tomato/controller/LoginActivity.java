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
import com.learning.tomato.dto.UserDTO;
import com.learning.tomato.until.ActivityCollector;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.until.NetttyClient.Simple;
import com.learning.tomato.until.netUtil.OkManager;
import com.learning.tomato.until.paramUtil.StringUtil;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private OkManager okManager=null;
    private String url= MyStaticResource.LOGINURL;
    Map<String,String> map=null;

//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 1:
//            }
//        }
//    };

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
                            resultMapping(userDTO.getFlag(),userDTO.getUserPO().getUserid());
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    MainActivity.actionStart(LoginActivity.this,R.drawable.default_icon,"15243643896");
                    new Simple().start();
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
     * @param userid
     */
    private void resultMapping(String flag, String userid) {
        switch (flag){
            case "0":
                ActivityCollector.finishAll();
                MainActivity.actionStart(LoginActivity.this,R.drawable.default_icon,userid);
                break;
            case "1":
                Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                break;
            case "-1":
                Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.clear();
        map=null;
    }

    public static void actionStart(Context context,String userid,String userpassword){
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra("userid",userid);
        intent.putExtra("userpassword",userpassword);
        context.startActivity(intent);
    }
}
