package com.learning.tomato.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.learning.tomato.R;
import com.learning.tomato.dao.ChattingMessage;

import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 聊天界面消息适配器
 * @date 2018/12/22 17:33
 */

public class ChattingMessageAdapter extends RecyclerView.Adapter<ChattingMessageAdapter.ViewHolder> {
    private static final String TAG = "ChattingMessageAdapter";
    private List<ChattingMessage> mChattingMessageList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout messageLeftLayout;
        LinearLayout messageRightLayout;
        ImageView friendImage;
        ImageView myImage;
        TextView messageLeft;
        TextView messageRight;

        public ViewHolder(View itemView) {
            super(itemView);
            messageLeftLayout=itemView.findViewById(R.id.messageleft_chatting_subleft);
            messageRightLayout=itemView.findViewById(R.id.messageright_chatting_subleft);
            friendImage=itemView.findViewById(R.id.friend_image);
            messageLeft=itemView.findViewById(R.id.chatting_messageleft);
            messageRight=itemView.findViewById(R.id.chatting_messageright);
            myImage=itemView.findViewById(R.id.my_image);
        }
    }

    public ChattingMessageAdapter(List<ChattingMessage> mChattingMessageList) {
        this.mChattingMessageList = mChattingMessageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chatting_subleft_messageitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChattingMessage chattingMessage=mChattingMessageList.get(position);
        if(chattingMessage.getType()==ChattingMessage.TYPE_RECEIVED){
            /**
             * 收到消息
             */
            holder.messageLeftLayout.setVisibility(View.VISIBLE);
            holder.messageRightLayout.setVisibility(View.GONE);
            holder.messageLeft.setText(chattingMessage.getContent());
            Glide.with(mContext).load(chattingMessage.getFriendImage()).into(holder.friendImage);
        }else if(chattingMessage.getType()==ChattingMessage.TYPE_SENT){
            /**
             * 发送消息
             */
            holder.messageLeftLayout.setVisibility(View.GONE);
            holder.messageRightLayout.setVisibility(View.VISIBLE);
            holder.messageRight.setText(chattingMessage.getContent());
            Glide.with(mContext).load(chattingMessage.getFriendImage()).into(holder.myImage);
        }
    }

    @Override
    public int getItemCount() {
        return mChattingMessageList.size();
    }
}
