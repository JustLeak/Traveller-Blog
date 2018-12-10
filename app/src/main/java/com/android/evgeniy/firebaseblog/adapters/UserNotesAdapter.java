package com.android.evgeniy.firebaseblog.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.repositories.UserNotesDao;

public class UserNotesAdapter extends BaseAdapter {
    private UserNotesDao userNotesDao;
    private LayoutInflater inflater;

    public UserNotesDao getUserNotesDao() {
        return userNotesDao;
    }

    public UserNotesAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.userNotesDao = new UserNotesDao(this);
    }

    @Override
    public int getCount() {
        return userNotesDao.getCount();
    }

    @Override
    public Object getItem(int position) {
        return userNotesDao.getOneById(position);
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
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.note = (TextView) convertView.findViewById(R.id.note);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.date.setText(userNotesDao.getOneById(position).getDate());
        holder.note.setText(userNotesDao.getOneById(position).getText());
        return convertView;
    }

    private static class ViewHolder {
        public TextView note;
        public TextView date;
    }
}
