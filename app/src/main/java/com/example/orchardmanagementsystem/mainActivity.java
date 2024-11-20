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

        // 获取第一个按钮
        Button firstBlock = findViewById(R.id.firstBlock);

        // 设置点击事件
        firstBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮后跳转到 CropMonitoringActivity
                Intent intent = new Intent(mainActivity.this, crop_monitoring_Activity.class);
                startActivity(intent);
            }
        });
    }
}
