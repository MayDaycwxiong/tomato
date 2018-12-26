package com.learning.tomato.controller;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.R;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 共享世界，分享中心
 * @date 2018/12/26 14:34
 */

public class MainActivitySharingWorldViewController {
    private static final String TAG = "MainActivitySharingWorldViewController";
    private Context context;
    private View view;

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

        Toolbar toolbar = view.findViewById(R.id.mainactivity_sharing_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.mainactivity_sharing_collapsing_toolbar);
        ImageView mainactivity_sharing_image = view.findViewById(R.id.mainactivity_sharing_backroundimage);
        TextView mainactivity_sharing_content = view.findViewById(R.id.mainactivity_sharing_content);
        baseActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = baseActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(sharing_name);
        Glide.with(context).load(R.drawable.chattingactivity_emotion).into(mainactivity_sharing_image);
        String sharing_content = generateSharingContent(sharing_name);
        mainactivity_sharing_content.setText(sharing_content);
    }

    private String generateSharingContent(String sharing_name) {
        StringBuffer sharingContent = new StringBuffer();
        for (int i = 0; i < 500; i++) {
            sharingContent.append(sharing_name);
        }
        return sharingContent.toString();
    }

}
