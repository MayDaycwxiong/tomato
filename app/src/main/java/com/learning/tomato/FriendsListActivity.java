package com.learning.tomato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FriendsListActivity extends BaseActivity {
    private static final String TAG = "FriendsListActivity";
    private List<FriendsGroup> friendsGroups;
    private RecyclerView friendsListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
//        ViewGroup linearLayout=findViewById(R.id.activity_friends_list);
        View view=getWindow().getDecorView();
        new FriendsListView(FriendsListActivity.this,view).onCreate();
       /* initData();
        Log.d(TAG, "onCreate: initData: "+friendsGroups.size());
        initActivity();
        Log.d(TAG, "onCreate: initActivity ");*/
    }

    /**
     * 初始化参数
     */
    private void initActivity() {
        friendsListRecyclerView=findViewById(R.id.activity_friends_list_RecyclerView);
        friendsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        HeaderAndFooterWrapperOfFriendsList headerAndFooterWrapperOfFriendsList=new HeaderAndFooterWrapperOfFriendsList(this,friendsGroups);
        addTitle(headerAndFooterWrapperOfFriendsList);
        friendsListRecyclerView.setAdapter(headerAndFooterWrapperOfFriendsList);
    }
    /**
     * 初始化好友列表
     */
    public void initData(){
        String[] titleName=getResources().getStringArray(R.array.friendsGroup);
        friendsGroups=new ArrayList<>();
        for(int i=0;i<4;i++) {
            FriendsGroup friendsGroup = new FriendsGroup();
            List<Friend> sublist=new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                Friend friend=new Friend();
                friend.setFriendHeadIcon(R.id.default_icon);
                friend.setFriendName(titleName[i]+" "+j);
                friend.setState("暂时离线");
                friend.setMotto("有志者事竟成！");
                sublist.add(friend);
            }
            friendsGroup.setTitleName(titleName[i]);
            friendsGroup.setFriendsList(sublist);
            friendsGroups.add(friendsGroup);
        }
    }

    /**
     * 初始化分组标题
     * @param headerAndFooterWrapperOfFriendsList
     */
    private void addTitle(HeaderAndFooterWrapperOfFriendsList headerAndFooterWrapperOfFriendsList) {
        for(FriendsGroup friendsGroup:friendsGroups){
            TextView title=new TextView(this);
            title.setText(friendsGroup.getTitleName());
            title.setGravity(Gravity.CENTER);
            headerAndFooterWrapperOfFriendsList.addHeaderView(title);
        }
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,FriendsListActivity.class);
        context.startActivity(intent);
    }

}
