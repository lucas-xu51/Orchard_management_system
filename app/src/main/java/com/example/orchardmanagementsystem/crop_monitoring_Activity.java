package com.example.orchardmanagementsystem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class crop_monitoring_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_monitoring);

        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton menuButton = findViewById(R.id.menuButton);

        // 设置每个图片点击监听器，跳转到第二个页面并传递作物信息
        setCropClickListener(R.id.tomato, "Tomato");
        setCropClickListener(R.id.potato, "Potato");
        setCropClickListener(R.id.apple, "Apple");
        setCropClickListener(R.id.cabbage, "Cabbage");
        setCropClickListener(R.id.carrot, "Carrot");
        setCropClickListener(R.id.pineapple, "Pineapple");

        backButton.setOnClickListener(v -> finish());

        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(crop_monitoring_Activity.this, mainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity after starting MainActivity
        });
    }

    private void setCropClickListener(int imageViewId, String cropName) {
        ImageView imageView = findViewById(imageViewId);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(crop_monitoring_Activity.this, CropDetailActivity.class);
            intent.putExtra("cropName", cropName);
            startActivity(intent);
        });
    }
}
