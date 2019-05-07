package com.learning.tomato.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private int imageResource;
    private static final String TAG = "MainActivity";
    private LinearLayout friendslist;
    private LinearLayout subright_msglist;
    private CoordinatorLayout subright_sharingcenter;
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
        Log.d(TAG, "onCreate: "+ imageResource);
        ImageView myHeadIcon=findViewById(R.id.activity_subleft_myHeadIcon);
        Glide.with(this).load(imageResource).into(myHeadIcon);

        baseActivity = this;
        mainActivityView = getWindow().getDecorView();
        subright_msglist = findViewById(R.id.mainactivity_subright_msglist);
        subright_sharingcenter = findViewById(R.id.mainactivity_subright_sharingcenter);
        friendslist = findViewById(R.id.activity_friends_list);

        ImageView mainactivity_msglist = findViewById(R.id.mainactivity_msglist_image);
        mainactivity_msglist.setOnClickListener(this);
        ImageView mainactivity_sharingcenter = findViewById(R.id.mainactivity_sharing_image);
        mainactivity_sharingcenter.setOnClickListener(this);
        ImageView mainactivity_friendslist = findViewById(R.id.mainactivity_friendslist_image);
        mainactivity_friendslist.setOnClickListener(this);
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
                new MainActivityMessageCenterViewController(MainActivity.this, mainActivityView,imageResource).onCreate();
                break;
            case R.id.mainactivity_friendslist_image:
                subright_sharingcenter.setVisibility(View.GONE);
                subright_msglist.setVisibility(View.GONE);
                friendslist.setVisibility(View.VISIBLE);
                new MainActivityFriendsListViewController(MainActivity.this, mainActivityView).onCreate();
                break;
            case R.id.mainactivity_sharing_image:
                subright_sharingcenter.setVisibility(View.VISIBLE);
                subright_msglist.setVisibility(View.GONE);
                friendslist.setVisibility(View.GONE);
                new MainActivitySharingWorldViewController(MainActivity.this, mainActivityView).onCreate(baseActivity);
        }
    }

    public static void actionStart(Context context, int myImage) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("myHeadIcon", myImage);
        context.startActivity(intent);
    }
}