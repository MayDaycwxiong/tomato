package com.learning.tomato;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: ChattingfriendAdapter 自定义适配器
 * @date 2018/12/21 14:33
 */

public class ChattingfriendAdapter extends RecyclerView.Adapter<ChattingfriendAdapter.ViewHolder>{

    private List<Chattingfriend> mChattingfriendList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageId;
        TextView name;
        TextView time;
        TextView message;
        public ViewHolder(View itemView) {
            super(itemView);
            imageId=itemView.findViewById(R.id.imageId);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            message=itemView.findViewById(R.id.message);
        }
    }

    public ChattingfriendAdapter(List<Chattingfriend> mChattingfriendList) {
        this.mChattingfriendList = mChattingfriendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_subright_friendlist_chatingfrienditem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chattingfriend chattingfriend=mChattingfriendList.get(position);
        holder.imageId.setImageResource(chattingfriend.getImageId());
        holder.name.setText(chattingfriend.getName());
        holder.time.setText(chattingfriend.getTime());
        holder.message.setText(chattingfriend.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChattingfriendList.size();
    }
}
