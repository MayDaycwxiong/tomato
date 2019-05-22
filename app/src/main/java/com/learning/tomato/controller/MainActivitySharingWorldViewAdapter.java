package com.learning.tomato.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.learning.tomato.R;
import com.learning.tomato.dao.SharingActivityJson;
import com.learning.tomato.dto.sharings.ActivityPO;
import com.learning.tomato.until.paramUtil.DateTranslate;

import java.net.ConnectException;
import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2019/5/19 13:56
 */

public class MainActivitySharingWorldViewAdapter extends RecyclerView.Adapter<MainActivitySharingWorldViewAdapter.ViewHolder> {

    private List<ActivityPO> activityPOList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userIcon;
        TextView userName;
        TextView sendTime;
        TextView content;
        View activityView;

        public ViewHolder(View itemView) {
            super(itemView);
            userIcon=itemView.findViewById(R.id.userIcon);
            userName=itemView.findViewById(R.id.userName);
            sendTime=itemView.findViewById(R.id.sendTime);
            content=itemView.findViewById(R.id.content);
            activityView=itemView;
        }
    }

    public MainActivitySharingWorldViewAdapter(Context context,List<ActivityPO> list){
        this.context=context;
        this.activityPOList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.activiy_main_sharing_world_acitvityitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityPO activityPO=activityPOList.get(position);
        SharingActivityJson json= JSON.parseObject(activityPO.getContentjson(),SharingActivityJson.class);
        Glide.with(context).load(json.getUsericon()).into(holder.userIcon);
        holder.userName.setText(json.getUsername());
        holder.sendTime.setText(DateTranslate.translateToString(activityPO.getReceivetime()));
        holder.content.setText(json.getUsercontent());
    }

    @Override
    public int getItemCount() {
        if(activityPOList==null){
            return 0;
        }
        return activityPOList.size();
    }

}
