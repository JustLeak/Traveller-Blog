package com.android.evgeniy.firebaseblog.listeners;

import android.support.annotation.NonNull;

import com.android.evgeniy.firebaseblog.listeners.managers.RecyclerListenersManager;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UserValueEventListener implements ValueEventListener {
    private RecyclerListenersManager manager;

    public UserValueEventListener(RecyclerListenersManager manager) {
        this.manager = manager;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        UserDetails details = dataSnapshot.getValue(UserDetails.class);
        manager.getAdapter().getUsersContainer().addOrReplace(details);
        manager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
