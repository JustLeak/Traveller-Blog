package com.android.evgeniy.firebaseblog.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    private TextView note;
    private TextView date;

    public void setNote(String note) {
        this.note.setText(note);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public NoteViewHolder(View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.date);
        note = (TextView) itemView.findViewById(R.id.note);
    }
}
