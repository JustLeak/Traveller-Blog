package com.android.evgeniy.firebaseblog.listeners.managers;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class EventListenersManager {
    private HashMap<DatabaseReference, ArrayList<ChildEventListener>> childListenersMap;
    private HashMap<DatabaseReference, ArrayList<ValueEventListener>> valueListenersMap;

    EventListenersManager() {
        childListenersMap = new HashMap<>();
        valueListenersMap = new HashMap<>();
    }

    public void removeChildEventListener(ChildEventListener listener) {
        for (DatabaseReference key : childListenersMap.keySet()) {
            for (ChildEventListener existingListener : childListenersMap.get(key)) {
                if (existingListener.equals(listener)) {
                    key.removeEventListener(existingListener);
                    childListenersMap.get(key).remove(existingListener);

                    if (childListenersMap.get(key).isEmpty()) {
                        childListenersMap.remove(key);
                    }
                }
            }
        }
    }

    public void removeChildEventListener(DatabaseReference reference) {
        childListenersMap.remove(reference);
    }

    protected ChildEventListener addChildEventListener(DatabaseReference reference, ChildEventListener listener) {
        reference.addChildEventListener(listener);

        if (childListenersMap.containsKey(reference)) {
            childListenersMap.get(reference).add(listener);

        } else {
            ArrayList<ChildEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            childListenersMap.put(reference, listeners);
        }

        return listener;
    }

    public void removeValueEventListener(ValueEventListener listener) {
        for (DatabaseReference key : valueListenersMap.keySet()) {
            for (ValueEventListener existingListener : valueListenersMap.get(key)) {
                if (existingListener.equals(listener)) {
                    key.removeEventListener(existingListener);
                    valueListenersMap.get(key).remove(existingListener);

                    if (valueListenersMap.get(key).isEmpty()) {
                        valueListenersMap.remove(key);
                    }
                }
            }
        }
    }

    protected ValueEventListener addValueEventListener(DatabaseReference reference, ValueEventListener listener) {
        reference.addValueEventListener(listener);

        if (valueListenersMap.containsKey(reference)) {
            valueListenersMap.get(reference).add(listener);

        } else {
            ArrayList<ValueEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            valueListenersMap.put(reference, listeners);
        }

        return listener;
    }
}
