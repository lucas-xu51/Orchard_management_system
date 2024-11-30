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

        ImageButton btnBack = findViewById(R.id.backButton);
        ImageButton btnMenu = findViewById(R.id.menuButton);

        // Add click listener for Back Button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Task.this, mainActivity.class);
            startActivity(intent);
        });
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

