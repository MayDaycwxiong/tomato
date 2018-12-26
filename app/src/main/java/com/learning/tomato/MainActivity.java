package com.learning.tomato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        final View mainActivityView=getWindow().getDecorView();
        final LinearLayout subright_msglist = findViewById(R.id.mainactivity_subright_msglist);
        final CoordinatorLayout subright_sharingcenter = findViewById(R.id.mainactivity_subright_sharingcenter);
        final LinearLayout friendslist=findViewById(R.id.activity_friends_list);
        ImageView mainactivity_msglist=findViewById(R.id.mainactivity_msglist_image);
        ImageView mainactivity_sharingcenter=findViewById(R.id.mainactivity_sharing_image);
        ImageView mainactivity_friendslist=findViewById(R.id.mainactivity_friendslist_image);
        mainactivity_msglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subright_msglist.setVisibility(View.VISIBLE);
                subright_sharingcenter.setVisibility(View.GONE);
                friendslist.setVisibility(View.GONE);
                init_subright_msglist();
            }
        });
        mainactivity_sharingcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subright_sharingcenter.setVisibility(View.VISIBLE);
                subright_msglist.setVisibility(View.GONE);
                friendslist.setVisibility(View.GONE);
                init_subright_sharingcenter();
            }
        });
        mainactivity_friendslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subright_sharingcenter.setVisibility(View.GONE);
                subright_msglist.setVisibility(View.GONE);
                friendslist.setVisibility(View.VISIBLE);
                new FriendsListView(MainActivity.this,mainActivityView).onCreate();
            }
        });
    }

    public static void actionStart(Context context, String myImage) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("myImage", myImage);
        context.startActivity(intent);
    }

    /**
     * 初始化 msglist
     */
    private void init_subright_msglist() {
        List<Chattingfriend> chattingfriendList = new ArrayList<>();
        chattingfriendList=initChattingfriends(chattingfriendList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main_subright);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChattingfriendAdapter adapter = new ChattingfriendAdapter(chattingfriendList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化 消息列表 好友聊天数据
     */
    private List initChattingfriends(List chattingfriendList) {
        for (int i = 0; i < 5; i++) {
            Chattingfriend cf1 = new Chattingfriend(R.drawable.default_icon, "胡歌", "15:28", "什么时候结婚呀？");
            chattingfriendList.add(cf1);
            Chattingfriend cf2 = new Chattingfriend(R.drawable.mainactivity_exit, "六小龄童", "15:29", "我明年出演中美合作的西游记。");
            chattingfriendList.add(cf2);
            Chattingfriend cf3 = new Chattingfriend(R.drawable.mainactivity_setting, "周星驰", "15:27", "如果给我一次重新来过的机会，我会对那个女孩说三个字");
            chattingfriendList.add(cf3);
            Chattingfriend cf4 = new Chattingfriend(R.drawable.mainactivity_chat, "朱茵", "15:18", "曾经有一份真挚的爱情在我面前...");
            chattingfriendList.add(cf4);
            Chattingfriend cf5 = new Chattingfriend(R.drawable.mainactivity_share, "刘亦菲", "15:33", "神仙姐姐你好呀！");
            chattingfriendList.add(cf5);
        }
        return chattingfriendList;
    }

    /**
     * 初始化 sharingcenter
     */
    public void init_subright_sharingcenter() {
        String sharing_name = "我的主页";

        Toolbar toolbar = findViewById(R.id.mainactivity_sharing_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.mainactivity_sharing_collapsing_toolbar);
        ImageView mainactivity_sharing_image = findViewById(R.id.mainactivity_sharing_backroundimage);
        TextView mainactivity_sharing_content = findViewById(R.id.mainactivity_sharing_content);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(sharing_name);
        Glide.with(this).load(2131165310).into(mainactivity_sharing_image);
        String sharing_content = generateSharingContent(sharing_name);
        mainactivity_sharing_content.setText(sharing_content);
    }

    public String generateSharingContent(String sharing_name) {
        StringBuffer sharingContent = new StringBuffer();
        for (int i = 0; i < 500; i++) {
            sharingContent.append(sharing_name);
        }
        return sharingContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
