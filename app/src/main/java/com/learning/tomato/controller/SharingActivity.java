package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.R;
import com.learning.tomato.dao.SharingActivityJson;
import com.learning.tomato.dto.sharings.ActivityDTO;
import com.learning.tomato.service.netUtil.OkManager;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.until.paramUtil.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class SharingActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SharingActivity";
    private EditText content;
    private Map<String,String> map=null;
    private OkManager okManager=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        ImageView back=findViewById(R.id.sharing_activity_back);
        back.setOnClickListener(this);
        Button commit=findViewById(R.id.sharing_activity_commit);
        commit.setOnClickListener(this);
        content=findViewById(R.id.sharing_activity_content);
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,SharingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sharing_activity_back:
                SharingActivity.this.finish();
                break;
            case R.id.sharing_activity_commit:
                sharing();
                break;
        }
    }

    private void sharing() {
        if(StringUtil.isEmpty(content.getText().toString())){
            Toast.makeText(SharingActivity.this,"请输入动态内容",Toast.LENGTH_SHORT).show();
            return;
        }
        okManager=OkManager.getInstance();
        SharingActivityJson json=new SharingActivityJson();
        json.setUsericon(MyStaticResource.USERICON);
        json.setUsername(MyStaticResource.USERNAME);
        json.setUsercontent(content.getText().toString());
        String contentjson=JSON.toJSONString(json);
        map=new HashMap<>();
        map.put("contentjson",contentjson);
        map.put("userid",MyStaticResource.USERID);
        okManager.asynJsonObjectByRequest(MyStaticResource.ADDACTIVITY, map, new OkManager.Func1() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG,result);
                ActivityDTO activityDTO=JSON.parseObject(result,ActivityDTO.class);
                Log.e(TAG,"响应消息为:"+activityDTO);
                Log.e(TAG,"响应标识为:"+activityDTO.getFlag());
                resultMapping(activityDTO);
            }
        });
    }

    /**
     * 结果映射
     * @param activityDTO
     */
    private void resultMapping(ActivityDTO activityDTO) {
        if(activityDTO.getFlag().equals("0")){
            Log.e(TAG,"发表动态成功");
            Toast.makeText(SharingActivity.this,"发表动态成功",Toast.LENGTH_SHORT).show();
            SharingActivity.this.finish();
        }else if(activityDTO.getFlag().equals("1")){
            Toast.makeText(SharingActivity.this,"发表动态失败",Toast.LENGTH_SHORT).show();
        }
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
