package com.learning.tomato.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.tomato.R;
import com.learning.tomato.until.ActivityCollector;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 设置页面
 * @date 2019/5/9 10:23
 */

public class MainActivitySettingViewController implements View.OnClickListener{
    private Context context;
    private View view;
    private TextView baseInfo;
    private TextView clean;
    private TextView exit;
    private String userid;
    private ImageView detail;

    public MainActivitySettingViewController(Context context, View view, String userid){
        this.context=context;
        this.view=view;
        this.userid=userid;
    }

    public void onCreate(){
        initActivity();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        baseInfo=view.findViewById(R.id.baseinfo);
        baseInfo.setOnClickListener(this);
        detail=view.findViewById(R.id.activity_setting_image);
        detail.setOnClickListener(this);
        clean=view.findViewById(R.id.clean);
        clean.setOnClickListener(this);
        exit=view.findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.baseinfo:
            case R.id.activity_setting_image:
                UserBaseInfoActivity.actionStart(context,userid);
                break;
            case R.id.clean:
                break;
            case R.id.exit:
                dialog();
                break;
        }
    }
    private void dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
