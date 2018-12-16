package com.android.evgeniy.firebaseblog.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView note;
    private TextView date;
    private TextView time;
    private TextView email;

    public NoteViewHolder(View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.date);
        note = (TextView) itemView.findViewById(R.id.note);
        time = (TextView) itemView.findViewById(R.id.time);
        email = (TextView) itemView.findViewById(R.id.email);
    }

    public TextView getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note.setText(note);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }
}
