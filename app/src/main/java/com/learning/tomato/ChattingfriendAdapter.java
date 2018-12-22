package com.learning.tomato;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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

    public ChattingfriendAdapter(List<Chattingfriend> mChattingfriendList) {
        this.mChattingfriendList = mChattingfriendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_subright_friendlist_chatingfrienditem,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.chattingfriendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Chattingfriend chattingfriend=mChattingfriendList.get(position);
                ChattingActivity.startActivity(view.getContext(),chattingfriend.getName(),chattingfriend.getImageId(),init());
            }
        });
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

    /**
     * 初始化聊天信息
     * @return 聊天信息列表
     */
    private ArrayList<String> init() {
        int me=0;
        int other=1;
        ArrayList<String> chattingDatas=new ArrayList<>();
        for(int i=0;i<1;i++){
            String s1=other+"近来可好？";
            chattingDatas.add(s1);
            String s2=other+"我们好久不见了！";
            chattingDatas.add(s2);
            String s3=me+"糟糕透了，最近天天加班。。";
            chattingDatas.add(s3);
            String s4=other+"不会吧这么辛苦";
            chattingDatas.add(s4);
            String s5=other+"心疼你一秒钟，摸摸头";
            chattingDatas.add(s5);
            String s6=me+"没办法，公司最近赶业务";
            chattingDatas.add(s6);
            String s7=me+"你最近怎么样呢？";
            chattingDatas.add(s7);
            String s8=me+"应该挺悠闲的吧...";
            chattingDatas.add(s8);
            String s9=other+"空闲时间挺多的，公司没什么业务";
            chattingDatas.add(s9);
            String s10=me+"真羡慕你们！";
            chattingDatas.add(s10);
        }
        return chattingDatas;
    }
}
