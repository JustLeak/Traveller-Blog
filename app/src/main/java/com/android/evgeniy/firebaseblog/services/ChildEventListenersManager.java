package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.adapters.listeners.NotesChildEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public final class ChildEventListenersManager {
    private ChildEventListenerCreator creator;
    private HashMap<DatabaseReference, ArrayList<ChildEventListener>> listenersMap;

    private static ChildEventListenersManager instance = null;

    private ChildEventListenersManager() {
        listenersMap = new HashMap<>();
        creator = new ChildEventListenerCreator();
    }

    public static ChildEventListenersManager getInstance() {
        if (instance == null)
            instance = new ChildEventListenersManager();

        return instance;
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

    public ChildEventListener addChildEventListener(ListenerType type, DatabaseReference reference) {
        ChildEventListener newListener = creator.createListener(type);
        reference.addChildEventListener(newListener);

        if (listenersMap.containsKey(reference)) {
            listenersMap.get(reference).add(newListener);

        } else {
            ArrayList<ChildEventListener> listeners = new ArrayList<>();
            listeners.add(creator.createListener(type));
            listenersMap.put(reference, listeners);
        }

        return newListener;
    }

    public enum ListenerType {
        NOTE
    }

    private final class ChildEventListenerCreator {

        private ChildEventListener createListener(ChildEventListenersManager.ListenerType type) {
            ChildEventListener listener = null;

            switch (type) {
                case NOTE:
                    listener = new NotesChildEventListener();
            }
            return listener;
        }
    }
}
