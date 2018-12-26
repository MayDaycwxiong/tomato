package com.learning.tomato;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2018/12/26 10:54
 */

public class FriendsListView {
    private static final String TAG = "FriendsListView";
    private List<FriendsGroup> friendsGroups;
    private RecyclerView friendsListRecyclerView;
    private Context context;
    private View view;

    public FriendsListView(Context context,View view) {
        this.context = context;
        this.view=view;
    }

    public void onCreate() {
        initData();
        initActivity();
    }
    /**
     * 初始化参数
     */
    private void initActivity() {
        friendsListRecyclerView=view.findViewById(R.id.activity_friends_list_RecyclerView);
        friendsListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        HeaderAndFooterWrapperOfFriendsList headerAndFooterWrapperOfFriendsList=new HeaderAndFooterWrapperOfFriendsList(context,friendsGroups);
        addTitle(headerAndFooterWrapperOfFriendsList);
        friendsListRecyclerView.setAdapter(headerAndFooterWrapperOfFriendsList);
    }
    /**
     * 初始化好友列表
     */
    public void initData(){
        String[] titleName=context.getResources().getStringArray(R.array.friendsGroup);
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
            TextView title=new TextView(context);
            title.setText(friendsGroup.getTitleName());
            title.setGravity(Gravity.CENTER);
            headerAndFooterWrapperOfFriendsList.addHeaderView(title);
        }
    }
}
