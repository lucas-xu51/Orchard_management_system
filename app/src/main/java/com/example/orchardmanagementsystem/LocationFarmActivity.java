package com.example.orchardmanagementsystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class LocationFarmActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, String> markerDescriptions = new HashMap<>();
    private Marker lastClickedMarker = null;
    private static final String PREFS_NAME = "MarkersPrefs";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_farm);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 底部导航按钮逻辑
        ImageButton btnCrop = findViewById(R.id.crop);
        ImageButton btnWarehouse = findViewById(R.id.warehouse);
        ImageButton btnCalendar = findViewById(R.id.calendar);
        ImageButton btnMap = findViewById(R.id.map);

        btnCrop.setOnClickListener(v -> {
            Intent cropIntent = new Intent(LocationFarmActivity.this, crop_monitoring_Activity.class);
            startActivity(cropIntent);
        });

        btnWarehouse.setOnClickListener(v -> {
            Intent warehouseIntent = new Intent(LocationFarmActivity.this, Inventory_Management.class);
            startActivity(warehouseIntent);
        });

        btnCalendar.setOnClickListener(v -> {
            Intent calendarIntent = new Intent(LocationFarmActivity.this, Task.class);
            startActivity(calendarIntent);
        });

        // 返回和菜单按钮逻辑
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(LocationFarmActivity.this, mainActivity.class);
            startActivity(intent);
            finish();
        });

        // 加载地图
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

        // 加载已保存的标记
        loadMarkers();

        mMap.setOnMarkerClickListener(marker -> {
            if (marker.equals(lastClickedMarker)) {
                showEditOrDeleteDialog(marker);
            } else {
                marker.showInfoWindow();
            }
            lastClickedMarker = marker;
            return true;
        });

        // 设置长按监听器用于新增点
        mMap.setOnMapLongClickListener(this::showAddMarkerDialog);

        // 默认移动到一个初始位置
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.0537, -119.4106), 10));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void showAddMarkerDialog(LatLng latLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Marker");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Enter marker name");
        layout.addView(nameInput);

        final EditText descriptionInput = new EditText(this);
        descriptionInput.setHint("Enter marker description");
        layout.addView(descriptionInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String description = descriptionInput.getText().toString();

            Marker newMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet(description));

            markerDescriptions.put(newMarker, description);
            saveMarker(newMarker);
            newMarker.showInfoWindow();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showEditOrDeleteDialog(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Marker");

        final EditText input = new EditText(this);
        input.setText(markerDescriptions.get(marker));
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newDescription = input.getText().toString();
            marker.setSnippet(newDescription);
            markerDescriptions.put(marker, newDescription);
            saveMarker(marker);
            marker.showInfoWindow();
        });

        builder.setNegativeButton("Delete", (dialog, which) -> {
            markerDescriptions.remove(marker);
            marker.remove();
            deleteMarker(marker);
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void saveMarker(Marker marker) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String markerKey = "marker_" + marker.getPosition().latitude + "_" + marker.getPosition().longitude;

        editor.putString(markerKey, marker.getTitle() + ";" + marker.getSnippet());
        editor.apply();
    }

    private void deleteMarker(Marker marker) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String markerKey = "marker_" + marker.getPosition().latitude + "_" + marker.getPosition().longitude;

        editor.remove(markerKey);
        editor.apply();
    }

    private void loadMarkers() {
        Map<String, ?> allMarkers = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allMarkers.entrySet()) {
            String[] markerData = ((String) entry.getValue()).split(";");
            String title = markerData[0];
            String snippet = markerData[1];
            String[] latLngKey = entry.getKey().replace("marker_", "").split("_");
            LatLng position = new LatLng(Double.parseDouble(latLngKey[0]), Double.parseDouble(latLngKey[1]));

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(title)
                    .snippet(snippet));

            markerDescriptions.put(marker, snippet);
        }
    }
}
