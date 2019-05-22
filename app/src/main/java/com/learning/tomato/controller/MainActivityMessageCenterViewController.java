package com.learning.tomato.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.learning.tomato.R;
import com.learning.tomato.dao.ReceiveMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 消息中心，接收消息，管理消息
 * @date 2018/12/26 16:24
 */

public class MainActivityMessageCenterViewController {
    private Context context;
    private View view;
    public static int imageResource;

    public MainActivityMessageCenterViewController(Context context, View view,int imageResource) {
        this.context = context;
        this.view = view;
        this.imageResource=imageResource;
    }
    public void onCreate(){
        init_subright_msglist();
    }

    /**
     * 初始化 msglist
     */
    private void init_subright_msglist() {
        List<ReceiveMessage> messageList = new ArrayList<>();
        messageList = initChattingfriends(messageList);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_main_subright);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        MainActivityMessageCenterViewAdapter adapter = new MainActivityMessageCenterViewAdapter(messageList,imageResource);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化 消息列表 好友聊天数据
     */
    private List initChattingfriends(List chattingfriendList) {
        for (int i = 0; i < 5; i++) {
//            ReceiveMessage cf1 = new ReceiveMessage("15243643896",R.drawable.default_icon, "胡歌", "15:28", "什么时候结婚呀？");
//            chattingfriendList.add(cf1);
//            ReceiveMessage cf2 = new ReceiveMessage("15243643896",R.drawable.mainactivity_exit, "六小龄童", "15:29", "我明年出演中美合作的西游记。");
//            chattingfriendList.add(cf2);
//            ReceiveMessage cf3 = new ReceiveMessage("15243643896",R.drawable.mainactivity_setting, "周星驰", "15:27", "如果给我一次重新来过的机会，我会对那个女孩说三个字");
//            chattingfriendList.add(cf3);
//            ReceiveMessage cf4 = new ReceiveMessage("15243643896",R.drawable.mainactivity_chat, "朱茵", "15:18", "曾经有一份真挚的爱情在我面前...");
//            chattingfriendList.add(cf4);
//            ReceiveMessage cf5 = new ReceiveMessage("15243643896",R.drawable.mainactivity_share, "刘亦菲", "15:33", "神仙姐姐你好呀！");
//            chattingfriendList.add(cf5);
        }
        return chattingfriendList;
    }

}
