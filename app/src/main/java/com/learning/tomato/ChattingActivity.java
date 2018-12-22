package com.learning.tomato;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {
    private static final String TAG = "ChattingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Intent intent=getIntent();
        String friendName=intent.getStringExtra("friendName");
        int friendImage=intent.getIntExtra("friendImage",0);
        ArrayList<String> chattingDatas=intent.getStringArrayListExtra("chattingDatas");
        Log.d(TAG, "onCreate: "+friendName+"\n"+friendImage+"\n"+chattingDatas.toString()+"\n");

    }

    /**
     *
     * @param context   调用该方法的 Activity
     * @param friendName    好友备注 or 好友名称
     * @param friendImage   好友头像 uri
     * @param chattingDatas 最近 10 条 or 更多聊天记录
     */
    public static void startActivity(Context context, String friendName,int friendImage,ArrayList<String> chattingDatas){
        Intent intent=new Intent(context,ChattingActivity.class);
        intent.putExtra("friendName",friendName);
        intent.putExtra("friendImage",friendImage);
        intent.putExtra("chattingDatas",chattingDatas);
        context.startActivity(intent);
    }
}
