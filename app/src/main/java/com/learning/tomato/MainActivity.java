package com.learning.tomato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<Chattingfriend> chattingfriendList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        };

        initChattingfriends();
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChattingfriendAdapter adapter=new ChattingfriendAdapter(chattingfriendList);
        recyclerView.setAdapter(adapter);
    }

    public static void actionStart(Context context,String myImage){
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("myImage",myImage);
        context.startActivity(intent);
    }
    /**
     * 初始化 消息列表 好友聊天数据
     */
    private void initChattingfriends() {
        for(int i=0;i<5;i++){
            Chattingfriend cf1=new Chattingfriend(R.drawable.default_icon,"胡歌","15:28","什么时候结婚呀？");
            chattingfriendList.add(cf1);
            Chattingfriend cf2=new Chattingfriend(R.drawable.chat_left,"六小龄童","15:29","我明年出演中美合作的西游记。");
            chattingfriendList.add(cf2);
            Chattingfriend cf3=new Chattingfriend(R.drawable.chat_right,"周星驰","15:27","大话西游之无敌+");
            chattingfriendList.add(cf3);
            Chattingfriend cf4=new Chattingfriend(R.drawable.mainactivity_chat,"朱茵","15:18","曾经有一份真挚的爱情在我面前...");
            chattingfriendList.add(cf4);
            Chattingfriend cf5=new Chattingfriend(R.drawable.mainactivity_share,"刘亦菲","15:33","神仙姐姐你好呀！");
            chattingfriendList.add(cf5);
        }
    }
}
