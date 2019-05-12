package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.R;
import com.learning.tomato.dto.UserDTO;
import com.learning.tomato.dto.UserPO;
import com.learning.tomato.until.ActivityCollector;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.until.netUtil.OkManager;
import com.learning.tomato.until.paramUtil.ObjectUtil;
import com.learning.tomato.until.paramUtil.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册Activity
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    private OkManager okManager=null;
    private String url= MyStaticResource.REGISTERURL;
    private TextView userid;
    private TextView password;
    private Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userid = findViewById(R.id.register_userid);
        password = findViewById(R.id.register_password);
        final TextView confirmPassword = findViewById(R.id.register_confirmpassword);
        Button register=findViewById(R.id.register_register);
        okManager=OkManager.getInstance();
        getUserid();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map=new HashMap<>();
                if(StringUtil.isNotEmpty(password.getText().toString())&&StringUtil.isNotEmpty(confirmPassword.getText().toString())){
                    if(password.getText().toString().equals(confirmPassword.getText().toString())){
                        map.put("userid",userid.getText().toString());
                        map.put("userpassword",password.getText().toString());
                        okManager.asynJsonObjectByRequest(url, map, new OkManager.Func1() {
                            @Override
                            public void onResponse(String result) {
                                Log.e(TAG,result);
                                UserDTO userDTO= JSON.parseObject(result,UserDTO.class);
                                Log.e(TAG,"响应消息:"+userDTO);
                                Log.e(TAG,"响应标识:"+userDTO.getFlag());
                                resultMapping(userDTO);
                            }
                        });
                    }else{
                        Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     */
    private void getUserid() {
        okManager.asynJsonObjectByRequest(url, null, new OkManager.Func1() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG,result);
                UserDTO userDTO= JSON.parseObject(result,UserDTO.class);
                Log.e(TAG,"响应消息:"+userDTO);
                Log.e(TAG,"响应标识:"+userDTO.getFlag());
                resultMapping(userDTO);
            }
        });
    }

    /**
     * 返回消息映射
     * @param userDTO
     */
    private void resultMapping(UserDTO userDTO) {
        UserPO userPO=userDTO.getUserPO();
        if(userDTO.getFlag().equals("0")){
            if(ObjectUtil.isNotNull(userPO)&&StringUtil.isNotEmpty(userPO.getUserid())){
                Log.e(TAG,"申请账号成功");
                userid.setText(userPO.getUserid());
            }else if(ObjectUtil.isNull(userPO)){
                Toast.makeText(RegisterActivity.this,"账号注册成功",Toast.LENGTH_SHORT).show();
                ActivityCollector.finishAll();
                LoginActivity.actionStart(RegisterActivity.this,userid.getText().toString(),password.getText().toString());
            }
        }else{
            Log.e(TAG,"申请账号异常");
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(map!=null){
            map.clear();
            map=null;
        }
    }
}
