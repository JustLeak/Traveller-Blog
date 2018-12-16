package com.android.evgeniy.firebaseblog.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.adapters.holders.FriendViewHolder;
import com.android.evgeniy.firebaseblog.models.Friend;

public class ClickFriendRecyclerAdapter extends FriendsRecyclerAdapter implements View.OnClickListener {
    private final OnItemClickListener friendClickListener;
    private int position;

    public ClickFriendRecyclerAdapter(LayoutInflater inflater, OnItemClickListener listener) {
        super(inflater);
        friendClickListener = listener;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FriendViewHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setTag(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        friendClickListener.onItemClick(v, position);
    }

    public Friend getFriendByIndex(int index) {
        return super.getFriendByIndex(index);
    }

    public Friend getContextMenuSelectedFriend() {
        return super.getFriendByIndex(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
