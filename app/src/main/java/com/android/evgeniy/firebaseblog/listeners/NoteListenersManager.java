package com.android.evgeniy.firebaseblog.listeners;

import com.android.evgeniy.firebaseblog.adapters.NotesRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

public final class NoteListenersManager extends ChildEventListenersManager {
    private NotesRecyclerAdapter adapter;

    public NoteListenersManager(NotesRecyclerAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    public ChildEventListener createListener(DatabaseReference reference) {

        return new NoteChildEventListener(adapter);
    }
}
