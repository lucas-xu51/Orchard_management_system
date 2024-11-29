package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局文件

        // 获取buttons
        Button firstBlock = findViewById(R.id.firstBlock);
        Button secondBlock = findViewById(R.id.secondBlock);
        Button thirdBlock = findViewById(R.id.thirdBlock);
        Button fourthBlock = findViewById(R.id.fourthBlock); // 新增Farm Location按钮

        // 设置点击事件 - Crop Monitoring
        firstBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity.this, crop_monitoring_Activity.class);
                startActivity(intent);
            }
        });

        // 设置点击事件 - Irrigation
        secondBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity.this, Irrigation_addNewItem.class);
                startActivity(intent);
            }
        });

        // 设置点击事件 - Inventory Management
        thirdBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity.this, Inventory_Management.class);
                startActivity(intent);
            }
        });

        // 设置点击事件 - Farm Location
        fourthBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity.this, LocationFarmActivity.class);
                startActivity(intent);
            }
        });
    }
}
