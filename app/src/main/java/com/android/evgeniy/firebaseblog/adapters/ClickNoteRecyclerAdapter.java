package com.android.evgeniy.firebaseblog.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.adapters.holders.NoteViewHolder;

public class ClickNoteRecyclerAdapter extends NotesRecyclerAdapter implements View.OnClickListener {
    private final OnItemClickListener noteClickListener;

    public ClickNoteRecyclerAdapter(LayoutInflater inflater, OnItemClickListener listener) {
        super(inflater);
        noteClickListener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoteViewHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        noteClickListener.onItemClick(v, position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
