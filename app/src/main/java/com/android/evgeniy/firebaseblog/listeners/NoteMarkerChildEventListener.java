package com.android.evgeniy.firebaseblog.listeners;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.fragments.MapFragment;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.services.BitmapCreator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Objects;

public final class NoteMarkerChildEventListener implements ChildEventListener {
    private MapFragment map;
    private String uId;


    NoteMarkerChildEventListener(MapFragment map) {
        this.map = map;
        uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        UserNote note = dataSnapshot.getValue(UserNote.class);

        if (map.isVisible()) {
            MarkerOptions options = new MarkerOptions();
            Drawable drawable;
            if (uId.equals(note.getOwnerId())) {
                drawable = map.getResources().getDrawable(R.drawable.ic_note_green);
            } else {
                drawable = map.getResources().getDrawable(R.drawable.ic_note_red);
            }

            Bitmap bitmap = BitmapCreator.getBitmap(drawable);
            LatLng latLng = new LatLng(Double.valueOf(note.getLocation().getLat()), Double.valueOf(note.getLocation().getLng()));

            options.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .position(latLng);

            Marker marker = map.getmMap().addMarker(options);

            marker.setTag(note);

            note.setKey(dataSnapshot.getKey());
            map.getMarkersContainer().addMarker(marker);
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
