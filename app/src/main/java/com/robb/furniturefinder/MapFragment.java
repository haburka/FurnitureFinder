package com.robb.furniturefinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    RelativeLayout mapLayout;
    LinearLayout buttonLayout;
    Marker lastMarker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.content_map_fragment, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mapLayout = (RelativeLayout) v.findViewById(R.id.mapLayout);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 40.4385187;
        double longitude = -79.9622782;

        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnMapClickListener(this);

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(14).build()));
        buttonLayout = new LinearLayout(getActivity());
        return v;
    }

    @Override
    public void onMapClick(LatLng point) {
        if(buttonLayout.getVisibility() != View.GONE){
            if(lastMarker != null)
                lastMarker.remove();
            buttonLayout.setVisibility(View.GONE);
        }else{
            lastMarker = googleMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("New Listing"));
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(new CameraPosition.Builder().target(point).zoom(18).build()));
            Button newListing = new Button(getActivity());
            newListing.setText("Create New Listing");
            buttonLayout = new LinearLayout(getActivity());
            buttonLayout.addView(newListing);
            mapLayout.addView(buttonLayout);
            //bring up a view that lets them see a picture
            //add to favorites or manage
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Button addToFavorites = new Button(getActivity());
        addToFavorites.setText("Add to Favorites");
        Button viewListing = new Button(getActivity());
        viewListing.setText("View Listing");
        buttonLayout = new LinearLayout(getActivity());
        buttonLayout.addView(addToFavorites);
        buttonLayout.addView(viewListing);
        mapLayout.addView(buttonLayout);
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}