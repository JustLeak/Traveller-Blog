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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapFragment extends Fragment implements
        OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final float DEFAULT_ZOOM = 15F;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;
    private LocationCallback mLocationCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        initMap();
        getLocationPermission();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(view.getContext(), "No result", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Toast.makeText(view.getContext(), "Requested", Toast.LENGTH_SHORT).show();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                }
            }
        };

        Toast.makeText(view.getContext(), "Created", Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(view.getContext(), "Map is ready", Toast.LENGTH_LONG).show();

        if (mLocationPermissionGranted) {
            getLastLocation();
            startLocationUpdates();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                            moveCamera(location, DEFAULT_ZOOM);
                        }
                    }
                });

        /*mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                    moveCamera(location, DEFAULT_ZOOM);
                }
            }
        });*/
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

        boolean[] isGrantedPermissions = {ContextCompat.checkSelfPermission(view.getContext().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED,
                ContextCompat.checkSelfPermission(view.getContext().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED};

        for (boolean isGranted : isGrantedPermissions) {
            if (!isGranted) {
                mLocationPermissionGranted = false;



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
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000);
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                mLocationCallback,
                null);
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
                Toast.makeText(view.getContext(), "Permissions granted.", Toast.LENGTH_LONG).show();
            } else {
                mLocationPermissionGranted = false;
                Toast.makeText(view.getContext(), "Permissions are not granted.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
