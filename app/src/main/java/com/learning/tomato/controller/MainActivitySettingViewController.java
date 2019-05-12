package com.learning.tomato.controller;

import android.content.Context;
import android.view.View;
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
        clean=view.findViewById(R.id.clean);
        clean.setOnClickListener(this);
        exit=view.findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.baseinfo:
                UserBaseInfoActivity.actionStart(context,userid);
                break;
            case R.id.clean:
                break;
            case R.id.exit:
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
    }
}
