package com.robb.furniturefinder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(inflater));

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

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter(LayoutInflater inflater) {
            mWindow = inflater.inflate(R.layout.custom_info_window, null);
            mContents = inflater.inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            badge = R.drawable.stock_chair;

            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
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

