package com.android.evgeniy.firebaseblog.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.holders.NoteViewHolder;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<UserNote> allNotes;
    private List<ChildEventListener> listeners;
    private final WeakReference<LayoutInflater> inflater;

    public List<UserNote> getAllNotes() {
        return allNotes;
    }

    NotesRecyclerAdapter(LayoutInflater inflater) {
        this.inflater = new WeakReference<>(inflater);
        allNotes = new ArrayList<>();
        listeners = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listeners.add(new NotesChildEventListener(user.getUid()));
        listeners.add(new NotesChildEventListener("iny0Ts79aOORxE61sw3CLTWxlI72"));
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
        holder.setNote(allNotes.get(position).getText());
        holder.setDate(allNotes.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    private int findNoteIndexByKey(String key) {

        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    private class NotesChildEventListener implements ChildEventListener {
        private final String uId;
        private final DatabaseReference mRef;
        private final String notesPath;

        public NotesChildEventListener(String uId) {
            notesPath = uId + "/Notes";
            this.uId = uId;
            this.mRef = FirebaseDatabase.getInstance().getReference().child(notesPath);
            mRef.addChildEventListener(this);
        }

        public void deleteChildEventListener() {
            mRef.removeEventListener(this);
        }

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            UserNote note = dataSnapshot.getValue(UserNote.class);

            if (note != null) {
                note.setKey(dataSnapshot.getKey());
            }

            allNotes.add(note);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            UserNote userNote = dataSnapshot.getValue(UserNote.class);
            int index = findNoteIndexByKey(dataSnapshot.getKey());
            allNotes.set(index, userNote);
            notifyItemChanged(index);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            UserNote userNote = dataSnapshot.getValue(UserNote.class);
            int index = findNoteIndexByKey(userNote.getKey());
            allNotes.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
