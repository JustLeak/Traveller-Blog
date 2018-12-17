package com.android.evgeniy.firebaseblog.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.holders.FriendViewHolder;
import com.android.evgeniy.firebaseblog.models.Friend;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    private final WeakReference<LayoutInflater> inflater;
    private ArrayList<Friend> friends;

    FriendsRecyclerAdapter(LayoutInflater inflater) {
        this.inflater = new WeakReference<>(inflater);
        friends = new ArrayList<>();
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = this.inflater.get();

        if (inflater != null) {
            return new FriendViewHolder(inflater.inflate(R.layout.friend_item, parent, false));
        } else throw new RuntimeException("Activity died.");
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        holder.setEmail(friends.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void update(ArrayList<Friend> friends) {
        this.friends = friends;
        this.notifyDataSetChanged();
    }
}
