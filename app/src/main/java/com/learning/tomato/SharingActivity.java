package com.learning.tomato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class SharingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subright_sharingcenter);

        String sharing_name="我的主页";

        Toolbar toolbar=findViewById(R.id.mainactivity_sharing_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.mainactivity_sharing_collapsing_toolbar);
        ImageView mainactivity_sharing_image=findViewById(R.id.mainactivity_sharing_image);
        TextView mainactivity_sharing_content=findViewById(R.id.mainactivity_sharing_content);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(sharing_name);
        Glide.with(this).load(2131165310).into(mainactivity_sharing_image);
        String sharing_content=generateSharingContent(sharing_name);
        mainactivity_sharing_content.setText(sharing_content);
    }

    private String generateSharingContent(String sharing_name) {
        StringBuffer sharingContent=new StringBuffer();
        for(int i=0;i<500;i++){
            sharingContent.append(sharing_name);
        }
        return sharingContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,SharingActivity.class);
        context.startActivity(intent);
    }
}
