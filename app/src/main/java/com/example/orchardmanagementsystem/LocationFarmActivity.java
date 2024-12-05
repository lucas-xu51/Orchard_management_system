package com.example.orchardmanagementsystem;

import android.app.AlertDialog;
import android.content.Intent;
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

public class LocationFarmActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, String> markerDescriptions = new HashMap<>();
    private Marker lastClickedMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_farm);

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

        // 添加初始点
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

        markerDescriptions.put(kelownaMarker, kelownaMarker.getSnippet());
        markerDescriptions.put(vernonMarker, vernonMarker.getSnippet());
        markerDescriptions.put(lakeCountryMarker, lakeCountryMarker.getSnippet());

        mMap.setOnMapLoadedCallback(() -> kelownaMarker.showInfoWindow());
        mMap.setOnMarkerClickListener(marker -> {
            if (marker.equals(lastClickedMarker)) {
                showEditDescriptionDialog(marker);
            } else {
                marker.showInfoWindow();
            }
            lastClickedMarker = marker;
            return true;
        });

        // 移动到初始位置
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lakeCountry, 10));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // 设置长按监听器用于新增点
        mMap.setOnMapLongClickListener(latLng -> showAddMarkerDialog(latLng));
    }

    private void showEditDescriptionDialog(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Description for " + marker.getTitle());

        final EditText input = new EditText(this);
        input.setText(markerDescriptions.get(marker));
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newDescription = input.getText().toString();
            marker.setSnippet(newDescription);
            markerDescriptions.put(marker, newDescription);
            marker.showInfoWindow();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
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
            newMarker.showInfoWindow();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
