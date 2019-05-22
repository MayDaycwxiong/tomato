package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.learning.tomato.service.NettyServer.SimpleServer;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;
import com.learning.tomato.service.NetttyClient.Simple;
import com.learning.tomato.until.MyStaticResource;

import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private int imageResource;
    public String userid;
    private LinearLayout friendslist;
    private LinearLayout subright_msglist;
    private CoordinatorLayout subright_sharingcenter;
    private LinearLayout setting;
    private View mainActivityView;
    private BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent=getIntent();
        imageResource=intent.getIntExtra("myHeadIcon",0);
        userid=MyStaticResource.USERID;
        Log.d(TAG, "myHeadIcon="+ imageResource+" userid="+userid);
//        ImageView myHeadIcon=findViewById(R.id.activity_subleft_myHeadIcon);
//        Glide.with(this).load(imageResource).into(myHeadIcon);
//        myHeadIcon.setOnClickListener(this);

        baseActivity = this;
        mainActivityView = getWindow().getDecorView();
        subright_msglist = findViewById(R.id.mainactivity_subright_msglist);
        subright_sharingcenter = findViewById(R.id.mainactivity_subright_sharingcenter);
        friendslist = findViewById(R.id.activity_friends_list);
        setting=findViewById(R.id.activity_setting);

        ImageView mainactivity_msglist = findViewById(R.id.mainactivity_msglist_image);
        mainactivity_msglist.setOnClickListener(this);
        ImageView mainactivity_sharingcenter = findViewById(R.id.mainactivity_sharing_image);
        mainactivity_sharingcenter.setOnClickListener(this);
        ImageView mainactivity_friendslist = findViewById(R.id.mainactivity_friendslist_image);
        mainactivity_friendslist.setOnClickListener(this);
        ImageView mainactivity_setting=findViewById(R.id.mainactivity_setting_image);
        mainactivity_setting.setOnClickListener(this);

//        ImageView mainactivity_exit=findViewById(R.id.mainactivity_exit);
//        mainactivity_exit.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainactivity_msglist_image:
                subright_msglist.setVisibility(View.VISIBLE);
                subright_sharingcenter.setVisibility(View.GONE);
                friendslist.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
//                new Simple().start();
//                SimpleServer.getSimpleServerInstance(MyStaticResource.ANDROIDSERVERPORT);
                new MainActivityMessageCenterViewController(MainActivity.this, mainActivityView,imageResource).onCreate();
                break;
            case R.id.mainactivity_friendslist_image:
                friendslist.setVisibility(View.VISIBLE);
                subright_sharingcenter.setVisibility(View.GONE);
                subright_msglist.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
//                new Simple().start();
                new MainActivityFriendsListViewController(MainActivity.this, mainActivityView).onCreate();
                break;
            case R.id.mainactivity_sharing_image:
                subright_sharingcenter.setVisibility(View.VISIBLE);
                subright_msglist.setVisibility(View.GONE);
                friendslist.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
                new MainActivitySharingWorldViewController(MainActivity.this, mainActivityView).onCreate(baseActivity);
                break;
            case R.id.mainactivity_setting_image:
                setting.setVisibility(View.VISIBLE);
                friendslist.setVisibility(View.GONE);
                subright_sharingcenter.setVisibility(View.GONE);
                subright_msglist.setVisibility(View.GONE);
                new MainActivitySettingViewController(MainActivity.this,mainActivityView,userid).onCreate();
                break;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
