package com.android.evgeniy.firebaseblog.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.repositories.UserFriendsDao;

public class FriendsAdapter extends BaseAdapter {
    private UserFriendsDao userFriendsDao;
    private LayoutInflater inflater;

    public UserFriendsDao getUserFriendsDao() {
        return userFriendsDao;
    }

    public FriendsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.userFriendsDao = new UserFriendsDao(this);
    }

    @Override
    public int getCount() {
        return userFriendsDao.getCount();
    }

    @Override
    public Object getItem(int position) {
        return userFriendsDao.getOneById(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friend_item, null);
            holder = new ViewHolder();
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.id = (TextView) convertView.findViewById(R.id.friend_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.email.setText(userFriendsDao.getOneById(position).getEmail());
        holder.id.setText(userFriendsDao.getOneById(position).getId());
        return convertView;
    }

    private static class ViewHolder {
        TextView email;
        TextView id;
    }
}
