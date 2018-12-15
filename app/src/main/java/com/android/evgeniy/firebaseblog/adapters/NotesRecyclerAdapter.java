package com.android.evgeniy.firebaseblog.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.holders.NoteViewHolder;
import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.adapters.listeners.NoteListenersManager;
import com.android.evgeniy.firebaseblog.dataaccess.NotesContainer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final WeakReference<LayoutInflater> inflater;
    private final FirebaseUser user;
    private UserFriendsDao userFriendsDao;
    private NoteListenersManager listenersManager;

    NotesRecyclerAdapter(LayoutInflater inflater) {
        this.inflater = new WeakReference<>(inflater);

        listenersManager = new NoteListenersManager(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        userFriendsDao = new UserFriendsDao(user.getUid());
        userFriendsDao.getAllFriendsId(this);

    }

    public void setListeners(ArrayList<String> resultIdList) {
        String notesPath;
        DatabaseReference reference;
        resultIdList.add(user.getUid());
        for (String id : resultIdList) {
            notesPath = id + "/Notes";
            reference = FirebaseDatabase.getInstance().getReference().child(notesPath);
            listenersManager.addChildEventListener(reference);
        }
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.inflater.get();

        if (inflater != null) {
            return new NoteViewHolder(inflater.inflate(R.layout.note_item, parent, false));
        } else throw new RuntimeException("Activity died.");
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.setNote(NotesContainer.getInstance().getNotes().get(position).getText());
        holder.setDate(NotesContainer.getInstance().getNotes().get(position).getDate());

        if (NotesContainer.getInstance().getNotes().get(position).getOwnerId().equals(user.getUid())) {
            holder.getDate().setBackgroundColor(Color.GRAY);
            holder.getNote().setBackgroundColor(Color.WHITE);
        } else {
            holder.getDate().setBackgroundColor(Color.BLUE);
            holder.getNote().setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return NotesContainer.getInstance().getNotes().size();
    }
}
