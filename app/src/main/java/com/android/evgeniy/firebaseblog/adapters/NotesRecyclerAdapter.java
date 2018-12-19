package com.android.evgeniy.firebaseblog.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.holders.NoteViewHolder;
import com.android.evgeniy.firebaseblog.adapters.touchHelpers.ItemTouchHelperAdapter;
import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.dataaccess.UserNotesDao;
import com.android.evgeniy.firebaseblog.dataaccess.containers.NotesContainer;
import com.android.evgeniy.firebaseblog.dataaccess.containers.UserDetailsContainer;
import com.android.evgeniy.firebaseblog.listeners.managers.RecyclerListenersManager;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder> implements ItemTouchHelperAdapter {
    private final WeakReference<LayoutInflater> inflater;
    private RecyclerListenersManager listenersManager;

    private NotesContainer notesContainer;
    private UserDetailsContainer usersContainer;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    NotesRecyclerAdapter(LayoutInflater inflater) {
        this.inflater = new WeakReference<>(inflater);

        notesContainer = new NotesContainer();
        usersContainer = new UserDetailsContainer();

        listenersManager = new RecyclerListenersManager(this);

        listenersManager.addNotesChildEventListener(FirebaseDatabase.getInstance().getReference().child(user.getUid() + "/Notes"));
        listenersManager.addUserValueEventListener(FirebaseDatabase.getInstance().getReference().child(user.getUid() + "/Details"));

        UserFriendsDao userFriendsDao = new UserFriendsDao(user.getUid());
        userFriendsDao.getAllFriendsId(this);
    }

    NotesRecyclerAdapter(LayoutInflater inflater, String uId) {
        this.inflater = new WeakReference<>(inflater);

        notesContainer = new NotesContainer();
        usersContainer = new UserDetailsContainer();

        listenersManager = new RecyclerListenersManager(this);

        ArrayList<String> userId = new ArrayList<>();
        userId.add(uId);
        setListeners(userId);
    }

    public NotesContainer getNotesContainer() {
        return notesContainer;
    }

    public UserDetailsContainer getUsersContainer() {
        return usersContainer;
    }

    public void setListeners(ArrayList<String> resultIdList) {
        String path;
        DatabaseReference reference;

        for (String id : resultIdList) {
            path = id + "/Details";
            reference = FirebaseDatabase.getInstance().getReference().child(path);
            listenersManager.addUserValueEventListener(reference);

            path = id + "/Notes";
            reference = FirebaseDatabase.getInstance().getReference().child(path);
            listenersManager.addNotesChildEventListener(reference);
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
        UserNote note = notesContainer.get(position);
        holder.setNote(note.getText());
        holder.setDate(note.getDate());
        holder.setTime(note.getTime());
        holder.setEmail(note.getEmail());

        holder.setName(usersContainer.getNameByEmail(note.getEmail()));

        if (note.getOwnerId().equals(user.getUid())) {
            holder.getCardView().setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.colorMyNote));
        } else holder.getCardView().setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.colorBackground));
    }

    @Override
    public int getItemCount() {
        return notesContainer.size();
    }

    @Override
    public void onItemDismiss(int position) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(notesContainer.get(position).getOwnerId() + "/Notes/" + notesContainer.get(position).getKey());
        UserNotesDao userNotesDao = new UserNotesDao();
        userNotesDao.removeByReference(reference);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        UserNote note = notesContainer.remove(fromPosition);
        notesContainer.add(toPosition > fromPosition ? toPosition - 1 : toPosition, note);
        notifyItemMoved(fromPosition, toPosition);
    }
}
