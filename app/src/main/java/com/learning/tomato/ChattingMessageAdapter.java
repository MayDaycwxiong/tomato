package com.learning.tomato;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2018/12/22 17:33
 */

public class ChattingMessageAdapter extends RecyclerView.Adapter<ChattingMessageAdapter.ViewHolder> {

    private List<ChattingMessage> mChattingMessageList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout messageLeftLayout;
        LinearLayout messageRightLayout;
        TextView messageLeft;
        TextView messageRight;

        public ViewHolder(View itemView) {
            super(itemView);
            messageLeftLayout=itemView.findViewById(R.id.messageleft_chatting_subleft);
            messageRightLayout=itemView.findViewById(R.id.messageright_chatting_subleft);
            messageLeft=itemView.findViewById(R.id.chatting_messageleft);
            messageRight=itemView.findViewById(R.id.chatting_messageright);
        }
    }

    public ChattingMessageAdapter(List<ChattingMessage> mChattingMessageList) {
        this.mChattingMessageList = mChattingMessageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        }else if(chattingMessage.getType()==ChattingMessage.TYPE_SENT){
            /**
             * 发送消息
             */
            holder.messageLeftLayout.setVisibility(View.GONE);
            holder.messageRightLayout.setVisibility(View.VISIBLE);
            holder.messageRight.setText(chattingMessage.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mChattingMessageList.size();
    }
}
