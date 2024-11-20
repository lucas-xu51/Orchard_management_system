package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class inventory_screen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory_screen2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ScrollView scrollView = findViewById(R.id.scrollView);
        LinearLayout linearLayout = findViewById(R.id.linearlayout);

        String[] seeds = {"Tomato", "Potato", "Apple", "Cabbage", "Carrot", "Pineapple"};

        for (String seed : seeds) {
            // Create a TextView for each seed
            TextView textView = new TextView(this);
            textView.setText(seed);
            textView.setTextSize(18);
            textView.setPadding(16, 16, 16, 16);

            // Make the TextView clickable
            textView.setOnClickListener(v -> {
                Intent intent = new Intent(inventory_screen2.this, inventory_seed.class);
                intent.putExtra("SEED_NAME", seed); // Pass the seed name
                startActivity(intent);
            });

            // Add the TextView to the LinearLayout
            linearLayout.addView(textView);
        }


    }
}