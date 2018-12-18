package com.android.evgeniy.firebaseblog.listeners.managers;

import com.android.evgeniy.firebaseblog.fragments.MapFragment;
import com.android.evgeniy.firebaseblog.listeners.NoteMarkerChildEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

public final class MapListenersManager extends EventListenersManager {
    private MapFragment map;
    private String uId;

    public MapListenersManager(MapFragment map, String uId) {
        super();
        this.map = map;
        this.uId = uId;
    }

    public MapFragment getMap() {
        return map;
    }

    public String getuId() {
        return uId;
    }

    public ChildEventListener addNoteMarkerChildEventListener(DatabaseReference reference) {
        ChildEventListener listener = new NoteMarkerChildEventListener(this);
        return super.addChildEventListener(reference, listener);
    }
}
