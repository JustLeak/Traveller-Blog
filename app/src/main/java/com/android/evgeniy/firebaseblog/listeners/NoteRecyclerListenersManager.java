package com.android.evgeniy.firebaseblog.listeners;

import com.android.evgeniy.firebaseblog.adapters.NotesRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

public final class NoteRecyclerListenersManager extends ChildEventListenersManager {
    private NotesRecyclerAdapter adapter;

    public NoteRecyclerListenersManager(NotesRecyclerAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    public ChildEventListener createListener(DatabaseReference reference) {

        return new NoteRecyclerChildEventListener(adapter);
    }
}
