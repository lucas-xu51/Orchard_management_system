package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Task extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Set click listeners for crop images
        setCropClickListener(R.id.Tomato, "Tomato");
        setCropClickListener(R.id.potato, "Potato");
        setCropClickListener(R.id.apple, "Apple");
        setCropClickListener(R.id.cabbage, "Cabbage");
        setCropClickListener(R.id.carrot, "Carrot");
        setCropClickListener(R.id.pineapple, "Pineapple");

        ImageButton btnCrop = findViewById(R.id.crop);
        ImageButton btnWarehouse = findViewById(R.id.warehouse);
        ImageButton btnCalendar = findViewById(R.id.calendar);
        ImageButton btnMap = findViewById(R.id.map);

        btnCrop.setOnClickListener(v -> {
            // 跳转到 Crop Monitoring 页面
            Intent cropIntent = new Intent(Task.this, crop_monitoring_Activity.class);
            startActivity(cropIntent);
        });

        btnWarehouse.setOnClickListener(v -> {
            // 跳转到 Inventory Management 页面
            Intent warehouseIntent = new Intent(Task.this, Inventory_Management.class);
            startActivity(warehouseIntent);
        });



        btnMap.setOnClickListener(v -> {
            // 跳转到 LocationFarmActivity 页面
            Intent mapIntent = new Intent(Task.this, LocationFarmActivity.class);
            startActivity(mapIntent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        ImageButton btnMenu = findViewById(R.id.menuButton);

        btnBack.setOnClickListener(v -> finish());
        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(Task.this, mainActivity.class);
            startActivity(intent);
        });
    }

    private void setCropClickListener(int imageViewId, String cropName) {
        ImageView imageView = findViewById(imageViewId);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Task.this, SetTask.class);
            intent.putExtra("cropName", cropName);
            startActivity(intent);
        });
    }
}

