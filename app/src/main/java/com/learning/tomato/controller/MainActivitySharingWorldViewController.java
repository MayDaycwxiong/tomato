package com.learning.tomato.controller;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.learning.tomato.dao.SharingActivityJson;
import com.learning.tomato.dto.sharings.ActivityPO;
import com.learning.tomato.dto.sharings.GetActivitysDTO;
import com.learning.tomato.service.netUtil.OkManager;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.until.MyStaticResource;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 共享世界，分享中心
 * @date 2018/12/26 14:34
 */

public class MainActivitySharingWorldViewController {
    private static final String TAG = "SharingWorldViewContro";
    private Context context;
    private View view;
    private OkManager okManager=null;
    private Map<String,String> map=null;
    private List<ActivityPO> list=null;
    private RecyclerView recyclerView;
    public MainActivitySharingWorldViewController(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void onCreate(BaseActivity baseActivity) {
        init_subright_sharingcenter(baseActivity);
    }

    /**
     * 初始化 sharingcenter
     */
    public void init_subright_sharingcenter(BaseActivity baseActivity) {

        String sharing_name = "我的主页";

        initActivities();
        Toolbar toolbar = view.findViewById(R.id.mainactivity_sharing_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.mainactivity_sharing_collapsing_toolbar);
        ImageView mainactivity_sharing_image = view.findViewById(R.id.mainactivity_sharing_backroundimage);
        recyclerView=view.findViewById(R.id.mainactivity_sharing_center);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


        FloatingActionButton sharing_world_btn=view.findViewById(R.id.sharing_world_btn);
        sharing_world_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharingActivity.actionStart(context);
            }
        });
        baseActivity.setSupportActionBar(toolbar);
        //  系统默认的返回箭头去除
        ActionBar actionBar = baseActivity.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        collapsingToolbarLayout.setTitle(sharing_name);
        Glide.with(context).load(MyStaticResource.USERICON).into(mainactivity_sharing_image);
        Log.e(TAG,"start "+TAG);
    }

    private void initActivities() {
//        List<ActivityPO> list=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            SharingActivityJson sharingActivityJson=new SharingActivityJson();
//            sharingActivityJson.setUsername("如风");
//            sharingActivityJson.setUsericon("");
//            sharingActivityJson.setUsercontent("第一次测试");
//            ActivityPO activityPO=new ActivityPO();
//            activityPO.setActivityid(1);
//            activityPO.setContentjson(JSONObject.toJSONString(sharingActivityJson));
//            activityPO.setReceivetime(new Date());
//            activityPO.setUserid("15243843896");
//            list.add(activityPO);
//        }
//        return list;
        MyStaticResource.MTHREADPOOL.execute(new Runnable() {
            @Override
            public void run() {
                okManager = OkManager.getInstance();
                map = new HashMap<>();
                okManager.asynJsonObjectByRequest(MyStaticResource.GETFRIENDSACTIVITIES, null, new OkManager.Func1() {
                    @Override
                    public void onResponse(String result) {
                        Log.e(TAG, result);
                        GetActivitysDTO getActivitysDTO = JSON.parseObject(result, GetActivitysDTO.class);
                        Log.e(TAG, "响应消息:" + getActivitysDTO);
                        Log.e(TAG, "响应标识:" + getActivitysDTO.getFlag());
                        resultMapping(getActivitysDTO);
                    }
                });
            }
        });
    }

    private void resultMapping(GetActivitysDTO getActivitysDTO) {
        if(getActivitysDTO.getFlag().equals("0")){
            Log.e(TAG,"获取好友动态消息成功");
            list=getActivitysDTO.getActivityPOList();
            MainActivitySharingWorldViewAdapter adapter=new MainActivitySharingWorldViewAdapter(context,list);
            recyclerView.setAdapter(adapter);
        }else if(getActivitysDTO.getFlag().equals("1")){
            Log.e(TAG,"获取好友动态消息失败");
        }
    }

    private String generateSharingContent(String sharing_name) {
        StringBuffer sharingContent = new StringBuffer();
        for (int i = 0; i < 500; i++) {
            sharingContent.append(sharing_name);
        }
        return sharingContent.toString();
    }

}
