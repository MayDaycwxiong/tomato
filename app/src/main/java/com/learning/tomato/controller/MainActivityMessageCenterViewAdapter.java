package com.learning.tomato.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.tomato.R;
import com.learning.tomato.dao.ReceiveMessage;

import java.util.List;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: MainActivityMessageCenterViewAdapter 自定义适配器
 * @date 2018/12/21 14:33
 */

public class MainActivityMessageCenterViewAdapter extends RecyclerView.Adapter<MainActivityMessageCenterViewAdapter.ViewHolder>{

    private List<ReceiveMessage> mMessageList;
    private int imageResource;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageId;
        TextView name;
        TextView time;
        TextView message;
        View chattingfriendView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageId=itemView.findViewById(R.id.imageId);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            message=itemView.findViewById(R.id.message);
            chattingfriendView=itemView;
        }
    }

    public MainActivityMessageCenterViewAdapter(List<ReceiveMessage> mMessageList,int imageResource) {
        this.mMessageList = mMessageList;
        this.imageResource=imageResource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_message_center_messageitem,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.chattingfriendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                ReceiveMessage message = mMessageList.get(position);
//                ChattingActivity.startActivity(view.getContext(),message.getUsreid(), message.getName(), message.getImageId(),imageResource);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReceiveMessage message = mMessageList.get(position);
//        holder.imageId.setImageResource(message.getImageId());
        holder.name.setText(message.getName());
        holder.time.setText(message.getTime());
        holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
