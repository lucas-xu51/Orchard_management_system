package com.example.orchardmanagementsystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;

public class LocationFarmActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, String> markerDescriptions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_farm);

        // Back button functionality
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(LocationFarmActivity.this, mainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Load the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapContainer);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            throw new RuntimeException("Error: MapFragment not found in the layout");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers with descriptions
        LatLng kelowna = new LatLng(49.8801, -119.4436);
        LatLng vernon = new LatLng(50.2670, -119.2720);
        LatLng lakeCountry = new LatLng(50.0537, -119.4106);

        Marker kelownaMarker = mMap.addMarker(new MarkerOptions()
                .position(kelowna)
                .title("Kelowna Farm")
                .snippet("Classic crops include apples, cherries, and grapes for world-class wineries."));

        Marker vernonMarker = mMap.addMarker(new MarkerOptions()
                .position(vernon)
                .title("Vernon Farm")
                .snippet("Classic crops include apples, cherries, grapes (wine), and peaches."));

        Marker lakeCountryMarker = mMap.addMarker(new MarkerOptions()
                .position(lakeCountry)
                .title("Lake Country Farm")
                .snippet("Classic crops include apples, pears, and stone fruits like peaches and apricots."));

        // Store markers and their descriptions in a HashMap
        markerDescriptions.put(kelownaMarker, kelownaMarker.getSnippet());
        markerDescriptions.put(vernonMarker, vernonMarker.getSnippet());
        markerDescriptions.put(lakeCountryMarker, lakeCountryMarker.getSnippet());

        // Automatically show the info window for the first marker when the map loads
        mMap.setOnMapLoadedCallback(() -> kelownaMarker.showInfoWindow());

        // Set marker click listener
        mMap.setOnMarkerClickListener(marker -> {
            showEditDescriptionDialog(marker); // Allow user to edit description
            marker.showInfoWindow(); // Show the info window
            return true;
        });

        // Move camera to Kelowna and set zoom level to 10
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lakeCountry, 10));

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void showEditDescriptionDialog(Marker marker) {
        // Create an AlertDialog with an EditText to let the user edit the description
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Description for " + marker.getTitle());

        final EditText input = new EditText(this);
        input.setText(markerDescriptions.get(marker)); // Pre-fill current description
        builder.setView(input);

        // Save button
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newDescription = input.getText().toString();
            marker.setSnippet(newDescription); // Update snippet
            markerDescriptions.put(marker, newDescription); // Update HashMap
            marker.showInfoWindow(); // Refresh info window
        });

        // Cancel button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }
}
