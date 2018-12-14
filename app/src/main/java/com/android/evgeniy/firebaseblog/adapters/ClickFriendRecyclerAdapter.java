package com.android.evgeniy.firebaseblog.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.adapters.holders.FriendViewHolder;
import com.android.evgeniy.firebaseblog.models.Friend;

public class ClickFriendRecyclerAdapter extends FriendsRecyclerAdapter implements View.OnClickListener {
    private final OnItemClickListener friendClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ClickFriendRecyclerAdapter(LayoutInflater inflater, OnItemClickListener listener) {
        super(inflater);
        friendClickListener = listener;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FriendViewHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        friendClickListener.onItemClick(v, position);
    }

    public Friend getFriendByIndex(int index){
        return super.getFriendByIndex(index);
    }
}
