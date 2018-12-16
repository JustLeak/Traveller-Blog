package com.android.evgeniy.firebaseblog.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.holders.NoteViewHolder;
import com.android.evgeniy.firebaseblog.dataaccess.NotesContainer;
import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.listeners.NoteRecyclerListenersManager;
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
    private NoteRecyclerListenersManager listenersManager;
    private NotesContainer notesContainer;

    NotesRecyclerAdapter(LayoutInflater inflater) {
        this.inflater = new WeakReference<>(inflater);

        notesContainer = new NotesContainer();
        listenersManager = new NoteRecyclerListenersManager(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        userFriendsDao = new UserFriendsDao(user.getUid());
        userFriendsDao.getAllFriendsId(this);

    }

    public NotesContainer getNotesContainer() {
        return notesContainer;
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
        holder.setNote(notesContainer.getNotes().get(position).getText());
        holder.setDate(notesContainer.getNotes().get(position).getDate());
        holder.setTime(notesContainer.getNotes().get(position).getTime());
        holder.setEmail(notesContainer.getNotes().get(position).getOwnerId());


    }

    @Override
    public int getItemCount() {
        return notesContainer.getNotes().size();
    }
}
