package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.entity.ChattingMessage;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends BaseActivity {
    public final static String state = "online";
    private static final String TAG = "ChattingActivity";
    private List<ChattingMessage> mChattingMessageList = new ArrayList<>();
    private RecyclerView messageRecyclerView;
    private ChattingMessageAdapter chattingMessageAdapter;
    private TextView name;
    private int myFriendImage;
    private int imageResource;
    private TextView currentState;
    private EditText inputContent;
    private ImageView sendMessage;
    private ImageView left_slip;
    private DrawerLayout mChatting_drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        String friendName = intent.getStringExtra("friendName");
        myFriendImage = intent.getIntExtra("friendImage", 0);
        imageResource = intent.getIntExtra("imageResource", 0);
        Log.d(TAG, "onCreate: " + friendName + "\n" + myFriendImage + "\n" + mChattingMessageList.toString() + "\n");
        name = findViewById(R.id.name);
        name.setText(friendName);

        currentState = findViewById(R.id.state);
        currentState.setText(state);
        inputContent = findViewById(R.id.inputcontent);
        sendMessage = findViewById(R.id.sendmessage);

        left_slip = findViewById(R.id.chatting_left_slip);
        mChatting_drawerLayout = findViewById(R.id.activity_chatting_drawerLayout);

        messageRecyclerView = findViewById(R.id.recycler_view_chatting_subleft);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(layoutManager);
        initMessage();
        chattingMessageAdapter = new ChattingMessageAdapter(mChattingMessageList);
        messageRecyclerView.setAdapter(chattingMessageAdapter);

        /**
         * 发送消息，更新 RecyclerView 视图
         */
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputContent.getText().toString();
                if (!"".equals(message.trim())) {
                    ChattingMessage chattingMessage = new ChattingMessage(message, imageResource, ChattingMessage.TYPE_SENT);
                    mChattingMessageList.add(chattingMessage);
                    chattingMessageAdapter.notifyItemInserted(mChattingMessageList.size() - 1);
                    messageRecyclerView.scrollToPosition(mChattingMessageList.size() - 1);
                    inputContent.setText("");
                }
            }
        });
        /**
         * 点击左滑图片，实现侧滑效果，这里是侧滑在右边
         */
        left_slip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChatting_drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        /**
         * 解决 侧滑菜单滑出时，侧滑菜单下层的 RecyclerView 能响应滑动问题。
         */
        mChatting_drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * 初始化聊天信息
     */
    private void initMessage() {
        for (int i = 0; i < 1; i++) {
            String s1 = "近来可好？";
            ChattingMessage chattingMessage1 = new ChattingMessage(s1, myFriendImage, ChattingMessage.TYPE_RECEIVED);
            mChattingMessageList.add(chattingMessage1);
            String s2 = "我们好久不见了！";
            ChattingMessage chattingMessage2 = new ChattingMessage(s2, myFriendImage, ChattingMessage.TYPE_RECEIVED);
            mChattingMessageList.add(chattingMessage2);
            String s3 = "糟糕透了，最近天天加班。。";
            ChattingMessage chattingMessage3 = new ChattingMessage(s3, imageResource, ChattingMessage.TYPE_SENT);
            mChattingMessageList.add(chattingMessage3);
            String s4 = "不会吧这么辛苦";
            ChattingMessage chattingMessage4 = new ChattingMessage(s4, myFriendImage, ChattingMessage.TYPE_RECEIVED);
            mChattingMessageList.add(chattingMessage4);
            String s5 = "心疼你一秒钟，摸摸头";
            ChattingMessage chattingMessage5 = new ChattingMessage(s5, myFriendImage, ChattingMessage.TYPE_RECEIVED);
            mChattingMessageList.add(chattingMessage5);
            String s6 = "没办法，公司最近赶业务";
            ChattingMessage chattingMessage6 = new ChattingMessage(s6, imageResource, ChattingMessage.TYPE_SENT);
            mChattingMessageList.add(chattingMessage6);
            String s7 = "你最近怎么样呢？";
            ChattingMessage chattingMessage7 = new ChattingMessage(s7, imageResource, ChattingMessage.TYPE_SENT);
            mChattingMessageList.add(chattingMessage7);
            String s8 = "应该挺悠闲的吧...";
            ChattingMessage chattingMessage8 = new ChattingMessage(s8, imageResource, ChattingMessage.TYPE_SENT);
            mChattingMessageList.add(chattingMessage8);
            String s9 = "空闲时间挺多的，公司没什么业务，明天我请你吃饭，在橙子酒店";
            ChattingMessage chattingMessage9 = new ChattingMessage(s9, myFriendImage, ChattingMessage.TYPE_RECEIVED);
            mChattingMessageList.add(chattingMessage9);
            String s10 = "真羡慕你们！";
            ChattingMessage chattingMessage10 = new ChattingMessage(s10, imageResource, ChattingMessage.TYPE_SENT);
            mChattingMessageList.add(chattingMessage10);
        }
    }

    /**
     * @param context     调用该方法的 Activity
     * @param friendName  好友备注 or 好友名称
     * @param friendImage 好友头像 uri
     */
    public static void startActivity(Context context, String friendName, int friendImage, int imageResource) {
        Intent intent = new Intent(context, ChattingActivity.class);
        intent.putExtra("friendName", friendName);
        intent.putExtra("friendImage", friendImage);
        intent.putExtra("imageResource", imageResource);
        context.startActivity(intent);
    }
}
