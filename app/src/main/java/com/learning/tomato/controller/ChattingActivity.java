package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import com.alibaba.fastjson.JSON;
import com.learning.tomato.dao.ReceiveMessage;
import com.learning.tomato.dto.friends.FriendsIptablesDTO;
import com.learning.tomato.dto.friends.IptablesPO;
import com.learning.tomato.service.NettyServer.ClientHandler;
import com.learning.tomato.service.NettyServer.ClientHandlerImpl;
import com.learning.tomato.service.netUtil.OkManager;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.dao.ChattingMessage;
import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.until.paramUtil.DateTranslate;
import com.learning.tomato.until.paramUtil.ObjectUtil;
import com.learning.tomato.until.paramUtil.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends BaseActivity {
    public static String state = "online";
    private static final String TAG = "ChattingActivity";
    private List<ChattingMessage> mChattingMessageList = new ArrayList<>();
    private RecyclerView messageRecyclerView;
    private ChattingMessageAdapter chattingMessageAdapter;
    private TextView name;
    private String myFriendImage;
    private String imageResource;
    private TextView currentState;
    private EditText inputContent;
    private ImageView sendMessage;
    private ImageView left_slip;
    private DrawerLayout mChatting_drawerLayout;
    private OkManager okManager=null;
    private Map<String,String> map=null;

    private String friendId;
    public static String serverIp=null;
    public static String clientSelfIp=null;
    private String friendIP=null;
    public static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        okManager=OkManager.getInstance();
        Intent intent = getIntent();
        friendId=intent.getStringExtra("friendId");
        String friendName = intent.getStringExtra("friendName");
        myFriendImage = intent.getStringExtra("friendImage");
        imageResource = MyStaticResource.USERICON;
        Log.d(TAG, "onCreate: " + friendName + "\n" + myFriendImage + "\n" + mChattingMessageList.toString() + "\n");
        name = findViewById(R.id.name);
        name.setText(friendName);

        currentState = findViewById(R.id.state);
        currentState.setText(state);
        getFriendIp(friendId);

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
                final String message = inputContent.getText().toString();
                if (!"".equals(message.trim())) {
                    ChattingMessage chattingMessage = new ChattingMessage(message, imageResource, ChattingMessage.TYPE_SENT);
                    mChattingMessageList.add(chattingMessage);
                    chattingMessageAdapter.notifyItemInserted(mChattingMessageList.size() - 1);
                    messageRecyclerView.scrollToPosition(mChattingMessageList.size() - 1);
                    inputContent.setText("");
//                    friendIP="192.168.137.114";
                    MyStaticResource.MTHREADPOOL.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(StringUtil.isNotEmpty(friendIP)){
                                ClientHandlerImpl clientHandler=new ClientHandlerImpl();
                                if(ClientHandlerImpl.channelMap.containsKey(friendId)){
                                    Log.e(TAG,"已有通话Channel");
                                    ReceiveMessage sendMessgae=new ReceiveMessage(MyStaticResource.USERID,MyStaticResource.USERICON,MyStaticResource.USERNAME, DateTranslate.translateToString(new Date()),message);
                                    clientHandler.writeData(friendId,JSON.toJSONString(sendMessgae));
                                }else{
                                    Log.e(TAG,"创建通话Channel");
                                    clientHandler.createChannel(friendIP,MyStaticResource.ANDROIDSERVERPORT,friendId);
                                    ReceiveMessage sendMessgae=new ReceiveMessage(MyStaticResource.USERID,MyStaticResource.USERICON,MyStaticResource.USERNAME, DateTranslate.translateToString(new Date()),message);
                                    clientHandler.writeData(friendId,JSON.toJSONString(sendMessgae));
                                }
                            }
//                            ClientHandlerImpl clientHandler=new ClientHandlerImpl();
//                            clientHandler.createChannel("192.168.137.115",MyStaticResource.ANDROIDSERVERPORT,friendId);
//                            clientHandler.writeData(friendId,message);
                        }
                    });
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

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x001){
                    Bundle data=msg.getData();
                    String message=data.getString("RECEIVED");
                    Log.e(TAG,message);
                    ChattingMessage chattingMessage=new ChattingMessage(message,myFriendImage,ChattingMessage.TYPE_RECEIVED);
                    if(ObjectUtil.isNotNull(mChattingMessageList)){
                        mChattingMessageList.add(chattingMessage);
                        chattingMessageAdapter.notifyItemInserted(mChattingMessageList.size()-1);
                        messageRecyclerView.scrollToPosition(mChattingMessageList.size()-1);
                    }
                }
            }
        };
    }

    /**
     * 初始化聊天数据
     * @param friendId
     */
    private void getFriendIp(String friendId) {
        Log.e(TAG,"获取好友IP，friendId="+friendId+"  serverIp="+serverIp+"  clientSelfIp="+clientSelfIp);
        if(ClientHandlerImpl.channelMap.containsKey(friendId)){
            return;
        }
        if(StringUtil.isEmpty(friendId)||StringUtil.isEmpty(serverIp)||StringUtil.isEmpty(clientSelfIp)){
            state="offline";
            currentState.setText(state);
        }else{
            /**
             * 根据friendId 从服务器获取到好友IP地址，用于通讯
             */
            map=new HashMap<>();
            map.put("userid",friendId);
            map.put("serverip",serverIp);
            map.put("clientselfip",clientSelfIp);
            okManager.asynJsonObjectByRequest(MyStaticResource.FRIENDIPADDR, map, new OkManager.Func1() {
                @Override
                public void onResponse(String result) {
                    Log.e(TAG,result);
                    FriendsIptablesDTO friendsIptablesDTO= JSON.parseObject(result,FriendsIptablesDTO.class);
                    Log.e(TAG,"响应消息:"+friendsIptablesDTO.getIptablesPO());
                    Log.e(TAG,"响应标识:"+friendsIptablesDTO.getFlag());
                    resultMapping(friendsIptablesDTO);
                }
            });
        }
    }

    /**
     * 获取好友ip地址数据映射
     * @param friendsIptablesDTO
     */
    private void resultMapping(FriendsIptablesDTO friendsIptablesDTO) {
        IptablesPO iptablesPO=friendsIptablesDTO.getIptablesPO();
        if(ObjectUtil.isNotNull(iptablesPO)&&friendsIptablesDTO.getFlag().equals("0")){
            friendIP=StringUtil.isNotEmpty(iptablesPO.getClientselfip())?iptablesPO.getClientselfip():MyStaticResource.EMPTY;
            Log.e(TAG,"获取好友="+iptablesPO.getUserid()+"的IP地址成功，ip="+friendIP);
        }else if(friendsIptablesDTO.getFlag().equals("1")){
            Log.e(TAG,"好友不在线，未能获得好友IP");
            state="offline";
            currentState.setText(state);
        }
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


//    public static void startActivity(Context context, String userid,String friendName, int friendImage, int imageResource) {
//        Intent intent = new Intent(context, ChattingActivity.class);
//        intent.putExtra("friendId",userid);
//        intent.putExtra("friendName", friendName);
//        intent.putExtra("friendImage", friendImage);
//        context.startActivity(intent);
//    }
    /**
     * @param context     调用该方法的 Activity
     * @param friendName  好友备注 or 好友名称
     * @param friendImage 好友头像 uri
     */
    public static void friendListStartActivity(Context context, String userid,String friendName, String friendImage) {
        Intent intent = new Intent(context, ChattingActivity.class);
        intent.putExtra("friendId",userid);
        intent.putExtra("friendName", friendName);
        intent.putExtra("friendImage", friendImage);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(map!=null){
            map.clear();
            map=null;
        }
        if(mChattingMessageList.size()>0){
            mChattingMessageList.clear();
            mChattingMessageList=null;
        }
    }
}
