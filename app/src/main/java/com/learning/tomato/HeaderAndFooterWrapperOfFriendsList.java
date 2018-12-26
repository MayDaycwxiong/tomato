package com.learning.tomato;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2018/12/25 23:04
 */

public class HeaderAndFooterWrapperOfFriendsList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FRIEND = 1;
    private List<FriendsGroup> friendsGroups;
    private Context context;
    private SparseArrayCompat<View> mHeaderViews;
    private List<Integer> mHeaderIndex;
    private HashMap<Integer, List<Friend>> mFriendsMap;
    private SparseBooleanArray mBooleanMap;

    public HeaderAndFooterWrapperOfFriendsList(Context context, List<FriendsGroup> friendsGroups) {
        this.friendsGroups = friendsGroups;
        this.context = context;
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

        public FriendHolder(View itemView) {
            super(itemView);
            friendIcon = itemView.findViewById(R.id.activity_friends_list_friendIcon);
            friendName = itemView.findViewById(R.id.activity_friends_list_friendName);
            motto = itemView.findViewById(R.id.activity_friends_list_motto);
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
        if (viewType == TYPE_HEADER) {
            return new HeaderHolder(LayoutInflater.from(context).inflate(R.layout.activity_friends_list_titleitem, parent, false));
        }
        return new FriendHolder(LayoutInflater.from(context).inflate(R.layout.activity_friends_list_frienditem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position)) {
            ((HeaderHolder) holder).titleName.setOnClickListener(null);
            ((HeaderHolder) holder).titleName.setText(friendsGroups.get(getHeadRealCount(position)).getTitleName());
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
            List<Friend> friend = mFriendsMap.get(titleId);
            int titleOfPosition = mHeaderIndex.get(titleId);
            int friendIndex = position - titleOfPosition - 1;
//            ((FriendHolder) holder).friendIcon.setImageResource(friend.get(friendIndex).getFriendHeadIcon());
            ((FriendHolder) holder).friendName.setText(friend.get(friendIndex).getFriendName());
            ((FriendHolder) holder).motto.setText(friend.get(friendIndex).getMotto());
        }
    }

    private int getHeadRealCount(int position) {
        return mHeaderIndex.indexOf(new Integer(position));
    }

    private int getFriendsBottomTitle(int position) {
        for (int i = 0; i < friendsGroups.size(); i++) {
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

    @Override
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
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (isHeaderView(position)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFriendsCount();
    }

    private int getHeadersCount() {
        return friendsGroups.size();
    }

    private int getFriendsCount() {
        mHeaderIndex.clear();
        mFriendsMap.clear();
        int itemCount = 0;
        int friendsSize = 0;
        for (int i = 0; i < friendsGroups.size(); i++) {
            if (i != 0) {
                itemCount++;
            }
            mHeaderIndex.add(new Integer(itemCount));
            int friendsBottomTitle = getFriendsSize(i);
            itemCount += friendsBottomTitle;
            friendsSize += friendsBottomTitle;
            if (friendsBottomTitle > 0) {
                mFriendsMap.put(i, friendsGroups.get(i).getFriendsList());
            }
        }
        return friendsSize;
    }

    private int getFriendsSize(int i) {
        int friendsSizeBottomTitle = friendsGroups.get(i).getFriendsList().size();
        if (!mBooleanMap.get(i)) {
            friendsSizeBottomTitle = 0;
        }
        return friendsSizeBottomTitle;
    }
}
