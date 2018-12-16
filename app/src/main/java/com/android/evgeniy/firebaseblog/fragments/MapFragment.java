package com.android.evgeniy.firebaseblog.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.dataaccess.MarkersContainer;
import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.listeners.NoteMarkerListenersManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MapFragment extends Fragment implements
        OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final float DEFAULT_ZOOM = 15F;

    private View view;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;
    private LocationCallback mLocationCallback;

    private Marker mLocMarker;

    private FirebaseUser firebaseUser;
    private MarkersContainer markersContainer;
    private NoteMarkerListenersManager listenersManager;
    private UserFriendsDao userFriendsDao;

    public GoogleMap getmMap() {
        return mMap;
    }

    public MarkersContainer getMarkersContainer() {
        return markersContainer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        initMap();
        getLocationPermission();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        markersContainer = new MarkersContainer();
        listenersManager = new NoteMarkerListenersManager(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        userFriendsDao = new UserFriendsDao(firebaseUser.getUid());
        userFriendsDao.getAllFriendsId(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(getContext(), "No result", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Toast.makeText(getContext(), "Requested", Toast.LENGTH_SHORT).show();

                    if (mLocMarker != null)
                        mLocMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    else {
                        mLocMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                    }
                }
            }
        };

        Toast.makeText(getContext(), "Created", Toast.LENGTH_LONG).show();
        return view;
    }

    public void setListeners(ArrayList<String> resultIdList) {
        String notesPath;
        DatabaseReference reference;
        resultIdList.add(firebaseUser.getUid());
        for (String id : resultIdList) {
            notesPath = id + "/Notes";
            reference = FirebaseDatabase.getInstance().getReference().child(notesPath);
            listenersManager.addChildEventListener(reference);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(getContext(), "Map is ready", Toast.LENGTH_LONG).show();

        if (mLocationPermissionGranted) {
            getLastLocation();
            startLocationUpdates();
        }

        mMap.setOnMarkerClickListener(this);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = false;
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    mLocMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));

                    moveCamera(location, DEFAULT_ZOOM);
                }
            }
        });
    }

    private void moveCamera(Location location, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        boolean[] isGrantedPermissions = {ContextCompat.checkSelfPermission(getContext().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED,
                ContextCompat.checkSelfPermission(getContext().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED};

        for (boolean isGranted : isGrantedPermissions) {
            if (!isGranted) {
                mLocationPermissionGranted = false;

                requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE);
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }
        }
        mLocationPermissionGranted = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopLocationUpdates();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mLocationPermissionGranted)
            startLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = false;
            return;
        }

        LocationRequest locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000);
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                mLocationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (permissions.length == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                getLastLocation();
                startLocationUpdates();
                Toast.makeText(getContext(), "Permissions granted.", Toast.LENGTH_LONG).show();
            } else {
                mLocationPermissionGranted = false;
                Toast.makeText(getContext(), "Permissions are not granted.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() != null)
            Toast.makeText(getContext(), marker.getTag().toString(), Toast.LENGTH_SHORT).show();
        return false;
    }
}
