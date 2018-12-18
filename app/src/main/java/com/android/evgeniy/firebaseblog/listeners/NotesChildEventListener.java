package com.android.evgeniy.firebaseblog.listeners;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.evgeniy.firebaseblog.listeners.managers.RecyclerListenersManager;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class NotesChildEventListener implements ChildEventListener {
    private RecyclerListenersManager manager;

    public NotesChildEventListener(RecyclerListenersManager manager) {
        this.manager = manager;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        UserNote note = dataSnapshot.getValue(UserNote.class);

        if (note != null) {
            note.setKey(dataSnapshot.getKey());
        }

        manager.getAdapter().getNotesContainer().addNote(note);
        manager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        UserNote userNote = dataSnapshot.getValue(UserNote.class);
        manager.getAdapter().notifyItemChanged(manager.getAdapter().getNotesContainer().changeNote(userNote, dataSnapshot.getKey()));
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        manager.getAdapter().notifyItemRemoved(manager.getAdapter().getNotesContainer().deleteNote(dataSnapshot.getKey()));
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
