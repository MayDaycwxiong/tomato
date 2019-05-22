package com.learning.tomato.controller;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learning.tomato.R;
import com.learning.tomato.dao.Friend;
import com.learning.tomato.dao.FriendsGroup;
import com.learning.tomato.dao.ReceiveMessage;
import com.learning.tomato.dto.friends.FriendInfo;
import com.learning.tomato.dto.friends.UserGroupInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 好友分组适配器
 * @date 2018/12/25 23:04
 */

public class MainActivityFriendsListViewHeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "FriendsListHeaderFooter";
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FRIEND = 1;
//    private List<FriendsGroup> friendsGroups;
    private List<UserGroupInfo> userGroupInfos;
    private Context context;
    private SparseArrayCompat<View> mHeaderViews;
    private List<Integer> mHeaderIndex;
    private HashMap<Integer, List<FriendInfo>> mFriendsMap;
    private SparseBooleanArray mBooleanMap;

    public MainActivityFriendsListViewHeaderAndFooterAdapter(Context context, List<UserGroupInfo> userGroupInfos) {
//        this.friendsGroups = friendsGroups;
        this.userGroupInfos=userGroupInfos;
        mHeaderViews = new SparseArrayCompat<>();
        mHeaderIndex = new ArrayList<>();
        mFriendsMap = new HashMap<>();
        mBooleanMap = new SparseBooleanArray();
    }

    /**
     * 分组栏
     */
    static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView titleName;

        public HeaderHolder(View itemView) {
            super(itemView);
            titleName = itemView.findViewById(R.id.activity_friends_list_titleName);
        }
    }

    /**
     * friend 实体
     */
    static class FriendHolder extends RecyclerView.ViewHolder {
        ImageView friendIcon;
        TextView friendName;
        TextView motto;
        View friendItem;

        public FriendHolder(View itemView) {
            super(itemView);
            friendIcon = itemView.findViewById(R.id.activity_friends_list_friendIcon);
            friendName = itemView.findViewById(R.id.activity_friends_list_friendName);
            motto = itemView.findViewById(R.id.activity_friends_list_motto);
            friendItem=itemView;
        }
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(TYPE_HEADER, view);
    }

    private boolean isHeaderView(int position) {
        return mHeaderIndex.contains(new Integer(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        if (viewType == TYPE_HEADER) {
            return new HeaderHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_friends_list_titleitem, parent, false));
        }
        View view=LayoutInflater.from(context).inflate(R.layout.activity_main_friends_list_frienditem, parent, false);
        final FriendHolder friendHolder=new FriendHolder(view);
        friendHolder.friendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=friendHolder.getAdapterPosition();
                int titleId = getFriendsBottomTitle(position);
                List<FriendInfo> friend = mFriendsMap.get(titleId);
                int titleOfPosition = mHeaderIndex.get(titleId);
                int friendIndex = position - titleOfPosition - 1;
                FriendInfo friendInfo=friend.get(friendIndex);
                ChattingActivity.friendListStartActivity(view.getContext(),friendInfo.getFriendId(),friendInfo.getUserPO().getUsername(),friendInfo.getUserPO().getUsericon());
                Log.e(TAG,"当前位置是:"+position+" 真实的好友位置是："+friendIndex+"当前好友"+friend.get(friendIndex).toString());
//                friendHolder.friendItem
            }
        });
        return friendHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position)) {
            ((HeaderHolder) holder).titleName.setOnClickListener(null);
            ((HeaderHolder) holder).titleName.setText(userGroupInfos.get(getHeadRealCount(position)).getUserGroupPO().getUsergroupname());
            ((HeaderHolder) holder).titleName.setTag(getHeadRealCount(position));
            ((HeaderHolder) holder).titleName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    boolean isOpen = mBooleanMap.get(position);
                    mBooleanMap.put(position, !isOpen);
                    notifyDataSetChanged();
                }
            });
            return;
        } else {
            int titleId = getFriendsBottomTitle(position);
            List<FriendInfo> friend = mFriendsMap.get(titleId);
            int titleOfPosition = mHeaderIndex.get(titleId);
            int friendIndex = position - titleOfPosition - 1;
            ((FriendHolder) holder).friendName.setText(friend.get(friendIndex).getUserPO().getUsername());
            ((FriendHolder) holder).motto.setText(friend.get(friendIndex).getUserPO().getUsermotto());
            Glide.with(context).load(friend.get(friendIndex).getUserPO().getUsericon()).into(((FriendHolder) holder).friendIcon);
        }
    }


    private int getHeadRealCount(int position) {
        return mHeaderIndex.indexOf(new Integer(position));
    }

    private int getFriendsBottomTitle(int position) {
        for (int i = 0; i < userGroupInfos.size(); i++) {
            if (mHeaderIndex.get(i) > position) {
                return i - 1;
            }
        }
        return mHeaderIndex.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_FRIEND;
        }
    }

  /*  @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }*/

  /*  @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (isHeaderView(position)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }*/

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFriendsCount();
    }

    private int getHeadersCount() {
        if(userGroupInfos==null){
            return 0;
        }
        return userGroupInfos.size();
    }

    private int getFriendsCount() {
        mHeaderIndex.clear();
        mFriendsMap.clear();
        int itemCount = 0;
        int friendsSize = 0;
        for (int i = 0; i < userGroupInfos.size(); i++) {
            if (i != 0) {
                itemCount++;
            }
            mHeaderIndex.add(new Integer(itemCount));
            int friendsBottomTitle = getFriendsSize(i);
            itemCount += friendsBottomTitle;
            friendsSize += friendsBottomTitle;
            if (friendsBottomTitle > 0) {
//                mFriendsMap.put(i, friendsGroups.get(i).getFriendsList());
                mFriendsMap.put(i,userGroupInfos.get(i).getFriendInfoList());
            }
        }
        return friendsSize;
    }

    private int getFriendsSize(int i) {
//        int friendsSizeBottomTitle = friendsGroups.get(i).getFriendsList().size();
        int friendsSizeBottomTitle=userGroupInfos.get(i).getFriendInfoList().size();
        if (!mBooleanMap.get(i)) {
            friendsSizeBottomTitle = 0;
        }
        return friendsSizeBottomTitle;
    }
}
