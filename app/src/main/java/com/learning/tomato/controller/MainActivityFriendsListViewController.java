package com.learning.tomato.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.R;
import com.learning.tomato.dao.FriendsGroup;
import com.learning.tomato.dto.friends.FriendsListDTO;
import com.learning.tomato.dto.friends.FriendsOfUserGroupDTO;
import com.learning.tomato.dto.friends.UserGroupInfo;
import com.learning.tomato.service.netUtil.OkManager;
import com.learning.tomato.until.MyStaticResource;
import com.learning.tomato.until.paramUtil.StringUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 好友列表
 * @date 2018/12/26 10:54
 */

public class MainActivityFriendsListViewController {
    private static final String TAG = "FriendsListController";
    private List<FriendsGroup> friendsGroups;
    private RecyclerView friendsListRecyclerView;
    private Context context;
    private View view;
    private OkManager okManager=null;
    private Map<String,String> map=null;
    private List<UserGroupInfo> list;
    private Map<String,Integer> friendGroupMap=new HashMap<>();

    public MainActivityFriendsListViewController(Context context, View view) {
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
        ImageView addFriend=view.findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"addFriend button click!");
                if(friendGroupMap.size()>0){
                    String [] groups=new String[friendGroupMap.size()];
                    int i=0;
                    for(String value:friendGroupMap.keySet()){
                        groups[i++]=value;
                    }
                    addFriendDialog(groups);
                }else{
                    Toast.makeText(context,"好友分组信息不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 添加好友对话框
     * @param list
     */
    private void addFriendDialog(final String[] list) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View layout=layoutInflater.inflate(R.layout.addfriends_dialog, (ViewGroup) view.findViewById(R.id.addFriend_dialog));
        if(map==null){
            map=new HashMap<>();
        }else if(map.size()>0){
            map.clear();
        }
        map.put("userid",MyStaticResource.USERID);
        Log.e(TAG,"添加好友操作的 userid="+MyStaticResource.USERID);
        final EditText findId=layout.findViewById(R.id.frindId);
        final Spinner friendGroup=layout.findViewById(R.id.friendGroup);
        ArrayAdapter<String> friendGroupAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,list);
        friendGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendGroup.setAdapter(friendGroupAdapter);
        friendGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                map.put("usergroupid",friendGroupMap.get(list[i])+"");
                Log.e(TAG,"添加好友操作的 usergroupid="+friendGroupMap.get(list[i]));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new AlertDialog.Builder(context).setTitle("添加好友").setView(layout)
                .setNegativeButton("取消",null)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        map.put("friend",findId.getText().toString());
                        Log.e(TAG,"添加好友操作的 friend="+findId.getText().toString());
                        addFriend(map);
                    }
                }).show();
    }

    private void addFriend(final Map<String,String> map) {
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                if(map.containsKey("usergroupid")&& StringUtil.isNotEmpty(map.get("usergroupid"))
                        && map.containsKey("friend")&&StringUtil.isNotEmpty(map.get("friend"))){
                    okManager=OkManager.getInstance();
                    okManager.asynJsonObjectByRequest(MyStaticResource.ADDFRIEND, map, new OkManager.Func1() {
                        @Override
                        public void onResponse(String result) {
                            Log.e(TAG,result);
                            FriendsOfUserGroupDTO friendsOfUserGroupDTO= JSON.parseObject(result,FriendsOfUserGroupDTO.class);
                            Log.e(TAG,"响应消息:"+friendsOfUserGroupDTO);
                            Log.e(TAG,"响应标识:"+friendsOfUserGroupDTO.getFlag());
                            resultMapping(friendsOfUserGroupDTO);
                        }
                    });
                }else{
//                    Toast.makeText(context,"不能进行添加好友操作",Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"不能进行添加好友操作");
                }

            }
        });
    }

    /**
     * 添加好友消息映射
     * @param friendsOfUserGroupDTO
     */
    private void resultMapping(FriendsOfUserGroupDTO friendsOfUserGroupDTO) {
        if(friendsOfUserGroupDTO.getFlag().equals("0")){
            Log.e(TAG,"添加好有成功");
            Toast.makeText(context,"添加好友成功",Toast.LENGTH_SHORT).show();
        }else if(friendsOfUserGroupDTO.getFlag().equals("1")){
            Log.e(TAG,"添加好友失败");
            Toast.makeText(context,"添加好友失败",Toast.LENGTH_SHORT).show();
        }else if(friendsOfUserGroupDTO.getFlag().equals("-1")){
            Toast.makeText(context,"好友不存在",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化好友列表
     */
    public void initData(){
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                okManager=OkManager.getInstance();
                if(map==null){
                    map=new HashMap<>();
                }else if(map.size()>0){
                    map.clear();
                }
                map.put("userid",MyStaticResource.USERID);
                okManager.asynJsonObjectByRequest(MyStaticResource.GETFRIENDSLIST, map,new OkManager.Func1() {
                    @Override
                    public void onResponse(String result) {
                        Log.e(TAG,result);
                        FriendsListDTO friendsListDTO= JSON.parseObject(result,FriendsListDTO.class);
                        Log.e(TAG,"响应消息:"+friendsListDTO);
                        Log.e(TAG,"响应标识"+friendsListDTO.getFlag());
                        resultMapping(friendsListDTO);
                    }
                });
            }
        });
//        String result="{\n" +
//                "    \"userGroupInfoList\": [\n" +
//                "        {\n" +
//                "            \"userGroupPO\": {\n" +
//                "                \"usergroupid\": 3,\n" +
//                "                \"userid\": \"15243643896\",\n" +
//                "                \"usergroupname\": \"我的好友\",\n" +
//                "                \"usergroupflag\": null\n" +
//                "            },\n" +
//                "            \"friendInfoList\": [\n" +
//                "                {\n" +
//                "                    \"userPO\": {\n" +
//                "                        \"userid\": null,\n" +
//                "                        \"username\": \"崔哥\",\n" +
//                "                        \"userpassword\": null,\n" +
//                "                        \"usericon\": \"\",\n" +
//                "                        \"usersex\": \"男\",\n" +
//                "                        \"userbirthday\": 1557127221000,\n" +
//                "                        \"birthday\": null,\n" +
//                "                        \"userage\": 21,\n" +
//                "                        \"useraddr\": \"湖南\",\n" +
//                "                        \"usermotto\": \"无\",\n" +
//                "                        \"userrole\": \"职业选手\"\n" +
//                "                    },\n" +
//                "                    \"friendId\": \"1451780593\"\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"userGroupPO\": {\n" +
//                "                \"usergroupid\": 4,\n" +
//                "                \"userid\": \"15243643896\",\n" +
//                "                \"usergroupname\": \"我的同学\",\n" +
//                "                \"usergroupflag\": null\n" +
//                "            },\n" +
//                "            \"friendInfoList\": [\n" +
//                "                {\n" +
//                "                    \"userPO\": {\n" +
//                "                        \"userid\": null,\n" +
//                "                        \"username\": \"腿哥\",\n" +
//                "                        \"userpassword\": null,\n" +
//                "                        \"usericon\": \"\",\n" +
//                "                        \"usersex\": \"男\",\n" +
//                "                        \"userbirthday\": 1557386546000,\n" +
//                "                        \"birthday\": null,\n" +
//                "                        \"userage\": 21,\n" +
//                "                        \"useraddr\": \"福建\",\n" +
//                "                        \"usermotto\": \"小哥\",\n" +
//                "                        \"userrole\": \"瓜皮\"\n" +
//                "                    },\n" +
//                "                    \"friendId\": \"745267209\"\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"userGroupPO\": {\n" +
//                "                \"usergroupid\": 5,\n" +
//                "                \"userid\": \"15243643896\",\n" +
//                "                \"usergroupname\": \"我的同事\",\n" +
//                "                \"usergroupflag\": null\n" +
//                "            },\n" +
//                "            \"friendInfoList\": []\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"userGroupPO\": {\n" +
//                "                \"usergroupid\": 6,\n" +
//                "                \"userid\": \"15243643896\",\n" +
//                "                \"usergroupname\": \"我的亲人\",\n" +
//                "                \"usergroupflag\": null\n" +
//                "            },\n" +
//                "            \"friendInfoList\": [\n" +
//                "                {\n" +
//                "                    \"userPO\": {\n" +
//                "                        \"userid\": null,\n" +
//                "                        \"username\": \"如风\",\n" +
//                "                        \"userpassword\": null,\n" +
//                "                        \"usericon\": \"\",\n" +
//                "                        \"usersex\": \"男\",\n" +
//                "                        \"userbirthday\": 1440691200000,\n" +
//                "                        \"birthday\": null,\n" +
//                "                        \"userage\": 25,\n" +
//                "                        \"useraddr\": \"长沙市\",\n" +
//                "                        \"usermotto\": \"哭泣难受\",\n" +
//                "                        \"userrole\": \"学生\"\n" +
//                "                    },\n" +
//                "                    \"friendId\": \"15243643896\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"userPO\": {\n" +
//                "                        \"userid\": null,\n" +
//                "                        \"username\": \"小老弟\",\n" +
//                "                        \"userpassword\": null,\n" +
//                "                        \"usericon\": \"\",\n" +
//                "                        \"usersex\": \"女\",\n" +
//                "                        \"userbirthday\": 841330945000,\n" +
//                "                        \"birthday\": null,\n" +
//                "                        \"userage\": 22,\n" +
//                "                        \"useraddr\": \"杭州\",\n" +
//                "                        \"usermotto\": \"如果我爱上你的笑容，要怎么拥抱要怎么拥有\",\n" +
//                "                        \"userrole\": \"学生\"\n" +
//                "                    },\n" +
//                "                    \"friendId\": \"15243643898\"\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"flag\": \"0\"\n" +
//                "}";
//        FriendsListDTO friendsListDTO= JSON.parseObject(result,FriendsListDTO.class);
//        list=friendsListDTO.getUserGroupInfoList();
//        String[] titleName=context.getResources().getStringArray(R.array.friendsGroup);
//        friendsGroups=new ArrayList<>();
//        for(int i=0;i<4;i++) {
//            FriendsGroup friendsGroup = new FriendsGroup();
//            List<Friend> sublist=new ArrayList<>();
//            for (int j = 0; j < 25; j++) {
//                Friend friend=new Friend();
//                friend.setFriendHeadIcon(R.drawable.chattingactivity_state_offline);
//                friend.setFriendName(titleName[i]+" "+j);
//                friend.setState("暂时离线");
//                friend.setMotto("有志者事竟成！");
//                sublist.add(friend);
//            }
//            for (int j = 0; j < 25; j++) {
//                Friend friend=new Friend();
//                friend.setFriendHeadIcon(R.drawable.chattingactivity_emotion);
//                friend.setFriendName(titleName[i]+" "+j);
//                friend.setState("暂时离线");
//                friend.setMotto("有志者事竟成！");
//                sublist.add(friend);
//            }
//            friendsGroup.setTitleName(titleName[i]);
//            friendsGroup.setFriendsList(sublist);
//            friendsGroups.add(friendsGroup);
//        }
    }

    /**
     * 获取好友列表结果映射
     * @param friendsListDTO
     */
    private void resultMapping(FriendsListDTO friendsListDTO) {
        if(friendsListDTO.getFlag().equals("0")){
            Log.e(TAG,"获取好友列表成功");
            list=friendsListDTO.getUserGroupInfoList();
            for(UserGroupInfo userGroupInfo:list){
                friendGroupMap.put(userGroupInfo.getUserGroupPO().getUsergroupname(),userGroupInfo.getUserGroupPO().getUsergroupid());
            }
        }else if(friendsListDTO.getFlag().equals("1")){
            Log.e(TAG,"获取好友列表失败");
        }
        MainActivityFriendsListViewHeaderAndFooterAdapter mainActivityFriendsListViewHeaderAndFooterAdapter =new MainActivityFriendsListViewHeaderAndFooterAdapter(context,list);
//        addTitle(mainActivityFriendsListViewHeaderAndFooterAdapter);
        friendsListRecyclerView.setAdapter(mainActivityFriendsListViewHeaderAndFooterAdapter);
    }

    /**
     * 初始化分组标题
     * @param mainActivityFriendsListViewHeaderAndFooterAdapter
     */
    private void addTitle(MainActivityFriendsListViewHeaderAndFooterAdapter mainActivityFriendsListViewHeaderAndFooterAdapter) {
        for(FriendsGroup friendsGroup:friendsGroups){
            TextView title=new TextView(context);
            title.setText(friendsGroup.getTitleName());
            title.setGravity(Gravity.CENTER);
            mainActivityFriendsListViewHeaderAndFooterAdapter.addHeaderView(title);
        }
    }
}
