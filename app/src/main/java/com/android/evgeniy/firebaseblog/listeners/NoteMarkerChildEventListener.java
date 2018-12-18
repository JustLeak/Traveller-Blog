package com.android.evgeniy.firebaseblog.listeners;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.listeners.managers.MapListenersManager;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.services.BitmapCreator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public final class NoteMarkerChildEventListener implements ChildEventListener {
    private MapListenersManager manager;

    public NoteMarkerChildEventListener(MapListenersManager manager) {
        this.manager = manager;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        UserNote note = dataSnapshot.getValue(UserNote.class);

        if (manager.getMap().isVisible()) {
            MarkerOptions options = new MarkerOptions();
            Drawable drawable;
            if (manager.getuId().equals(note.getOwnerId())) {
                drawable = manager.getMap().getResources().getDrawable(R.drawable.ic_note_primary_24dp);
            } else {
                drawable = manager.getMap().getResources().getDrawable(R.drawable.ic_note_friend_24dp);
            }

            Bitmap bitmap = BitmapCreator.getBitmap(drawable);
            LatLng latLng = new LatLng(Double.valueOf(note.getLocation().getLat()), Double.valueOf(note.getLocation().getLng()));

            options.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .position(latLng);

            Marker marker = manager.getMap().getMap().addMarker(options);

            marker.setTag(note);
            marker.setTitle(note.getText());

            note.setKey(dataSnapshot.getKey());
            manager.getMap().getMarkersContainer().add(marker);
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
