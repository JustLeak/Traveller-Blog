package com.android.evgeniy.firebaseblog.listeners;

import android.app.Fragment;

import com.android.evgeniy.firebaseblog.fragments.MapFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

public final class NoteMarkerListenersManager extends ChildEventListenersManager {
    private MapFragment map;

    public NoteMarkerListenersManager(MapFragment map) {
        super();
        this.map = map;
    }

    @Override
    public ChildEventListener createListener(DatabaseReference reference) {

        return new NoteMarkerChildEventListener(map);
    }
}
