package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Inventory_Management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setCropClickListener(R.id.Tomato, "Tomato");
        setCropClickListener(R.id.Potato, "Potato");
        setCropClickListener(R.id.Apple, "Apple");
        setCropClickListener(R.id.Cabbage, "Cabbage");
        setCropClickListener(R.id.Carrot, "Carrot");
        setCropClickListener(R.id.Pineapple, "Pineapple");
    }

    private void setCropClickListener(int imageViewId, String cropName) {
        ImageView imageView = findViewById(imageViewId);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory_Management.this, inventory_screen2.class);
            intent.putExtra("cropName", cropName);
            startActivity(intent);
        });
    }
}