package com.android.evgeniy.firebaseblog.services;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ChildEventListenersManager {
    private HashMap<DatabaseReference, ArrayList<ChildEventListener>> listenersMap;

    public ChildEventListenersManager() {
        listenersMap = new HashMap<>();
    }

    public void removeChildEventListener(ChildEventListener listener) {
        for (DatabaseReference key : listenersMap.keySet()) {
            for (ChildEventListener existingListener : listenersMap.get(key)) {

                if (existingListener.equals(listener)) {
                    key.removeEventListener(existingListener);
                    listenersMap.get(key).remove(existingListener);

                    if (listenersMap.get(key).isEmpty()) {
                        listenersMap.remove(key);
                    }
                }
            }
        }
    }

    public ChildEventListener addChildEventListener(DatabaseReference reference) {
        ChildEventListener listener = createListener(reference);
        reference.addChildEventListener(listener);

        if (listenersMap.containsKey(reference)) {
            listenersMap.get(reference).add(listener);

        } else {
            ArrayList<ChildEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            listenersMap.put(reference, listeners);
        }

        return listener;
    }

    public abstract ChildEventListener createListener(DatabaseReference reference);
}
