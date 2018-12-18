package com.android.evgeniy.firebaseblog.listeners.managers;

import com.android.evgeniy.firebaseblog.adapters.NotesRecyclerAdapter;
import com.android.evgeniy.firebaseblog.listeners.NotesChildEventListener;
import com.android.evgeniy.firebaseblog.listeners.UserValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public final class RecyclerListenersManager extends EventListenersManager {
    private NotesRecyclerAdapter adapter;

    public RecyclerListenersManager(NotesRecyclerAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    public NotesRecyclerAdapter getAdapter() {
        return adapter;
    }

    public ChildEventListener addNotesChildEventListener(DatabaseReference reference) {
        ChildEventListener listener = new NotesChildEventListener(this);
        return super.addChildEventListener(reference, listener);
    }

    public ValueEventListener addUserValueEventListener(DatabaseReference reference) {
        ValueEventListener listener = new UserValueEventListener(this);
        return super.addValueEventListener(reference, listener);
    }
}
