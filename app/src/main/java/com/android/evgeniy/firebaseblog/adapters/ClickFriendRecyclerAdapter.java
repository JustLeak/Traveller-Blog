package com.android.evgeniy.firebaseblog.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.adapters.holders.FriendViewHolder;

public class ClickFriendRecyclerAdapter extends FriendsRecyclerAdapter implements View.OnClickListener,
        View.OnLongClickListener {
    private final OnItemClickListener friendClickListener;
    private final OnItemLongClickListener friendLongClickListener;

    public ClickFriendRecyclerAdapter(LayoutInflater inflater, OnItemClickListener listener, OnItemLongClickListener longListener) {
        super(inflater);
        friendClickListener = listener;
        friendLongClickListener = longListener;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FriendViewHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        friendClickListener.onItemClick(v, position);
    }

    @Override
    public boolean onLongClick(View v) {
        Integer position = (Integer) v.getTag();
        return friendLongClickListener.onItemLongClick(v, position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}
