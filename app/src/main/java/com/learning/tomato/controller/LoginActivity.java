package com.learning.tomato.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.entity.Service.UserDTO;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.until.netUtil.OkManager;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private OkManager okManager=null;
    private String url="http://192.168.137.1:8080/websys_web_war_exploded/users/login.pub";
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
        final EditText userNameText=findViewById(R.id.username);
        final EditText passwordText=findViewById(R.id.password);
        
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
                if(!userNameText.getText().toString().equals("")&&!passwordText.getText().toString().equals("")){
                    Log.e(TAG,"账号："+userNameText.getText().toString());
                    Log.e(TAG,"密码："+passwordText.getText().toString());
                    map=new HashMap<>();
                    map.put("userid",userNameText.getText().toString());
                    map.put("userpassword",passwordText.getText().toString());
                    okManager.asynJsonObjectByRequest(url, map, new OkManager.Func1() {
                        @Override
                        public void onResponse(String result) {
                            Log.e(TAG,result);
                            UserDTO userDTO= JSON.parseObject(result,UserDTO.class);
                            Log.e(TAG,"响应消息:"+userDTO);
                            Log.e(TAG,"响应标识:"+userDTO.getFlag());
                            resultMapping(userDTO.getFlag());
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    MainActivity.actionStart(LoginActivity.this,R.drawable.default_icon);
                }
            }
        });
    }

    /**
     * 返回结果映射处理
     * @param flag
     */
    private void resultMapping(String flag) {
        switch (flag){
            case "0":
                MainActivity.actionStart(LoginActivity.this,R.drawable.default_icon);
                break;
            case "1":
                Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                break;
            case "-1":
                Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
