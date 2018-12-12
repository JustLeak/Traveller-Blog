package com.android.evgeniy.firebaseblog.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.holders.NoteViewHolder;
import com.android.evgeniy.firebaseblog.models.UserNote;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private ArrayList<UserNote> userNotes;
    private final WeakReference<LayoutInflater> inflater;

    public NotesRecyclerAdapter(LayoutInflater inflater) {
        this.inflater = new WeakReference<>(inflater);
        userNotes = new ArrayList<>();
    }

    public void setUserNotes(ArrayList<UserNote> userNotes) {
        this.userNotes = userNotes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.inflater.get();

        if (inflater != null) {
            return new NoteViewHolder(inflater.inflate(R.layout.item, parent, false));
        } else throw new RuntimeException("Activity died");
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.setNote(userNotes.get(position).getText());
        holder.setDate(userNotes.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return userNotes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
