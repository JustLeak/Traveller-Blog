package com.android.evgeniy.firebaseblog.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserNote;

import java.util.ArrayList;

public class UserNotesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<UserNote> userNotes;

    public UserNotesAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        userNotes = new ArrayList<>();
    }

    public void setUserNotes(ArrayList<UserNote> userNotes) {
        this.userNotes = userNotes;
    }

    @Override
    public int getCount() {
        return userNotes.size();

    }

    @Override
    public Object getItem(int position) {
        return userNotes.get(position);
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
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.date.setText(userNotes.get(position).getDate());
        holder.note.setText(userNotes.get(position).getText());
        return convertView;
    }

    private static class ViewHolder {
        public TextView note;
        public TextView date;
    }
}
