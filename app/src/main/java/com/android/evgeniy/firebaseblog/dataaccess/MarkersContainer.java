package com.android.evgeniy.firebaseblog.dataaccess;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public final class MarkersContainer {
    ArrayList<Marker> markers;

    public MarkersContainer() {
        this.markers = new ArrayList<>();
    }

    public void addMarker(Marker marker) {
        markers.add(marker);
    }
}
