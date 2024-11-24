package com.example.orchardmanagementsystem;

import android.annotation.SuppressLint;
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

        // 获取button
        Button firstBlock = findViewById(R.id.firstBlock);
        Button secondBlock = findViewById(R.id.secondBlock);
        Button thirdBlock = findViewById(R.id.thirdBlock);

        // 设置点击事件
        firstBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮后跳转到 CropMonitoringActivity
                Intent intent = new Intent(mainActivity.this, crop_monitoring_Activity.class);
                startActivity(intent);
            }
        });

        secondBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮后跳转到 CropMonitoringActivity
                Intent intent = new Intent(mainActivity.this, Irrigation_addNewItem.class);
                startActivity(intent);
            }
        });

        // 设置点击事件-inventory
        thirdBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮后跳转到 inventory activity
                Intent intent = new Intent(mainActivity.this, Inventory_Management.class);
                startActivity(intent);
            }
        });
    }
}
