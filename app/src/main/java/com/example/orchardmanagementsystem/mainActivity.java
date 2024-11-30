package com.example.orchardmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class mainActivity extends AppCompatActivity {

    private List<Task2> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局文件

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView is null. Check the ID in the XML layout.");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Initialize task list
        taskList = new ArrayList<>();

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList, position -> {
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(taskAdapter);


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
                Intent intent = new Intent(mainActivity.this, Task.class);
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
