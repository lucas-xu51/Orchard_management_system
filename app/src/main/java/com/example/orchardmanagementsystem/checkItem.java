package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class checkItem extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private String selectedDateKey;
    private String cropName; // Add this variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);

        // Retrieve cropName from Intent
        Intent intent = getIntent();
        cropName = intent.getStringExtra("cropName");

        // Initialize RecyclerView
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        // Initialize CalendarView
        CalendarView calendarView = findViewById(R.id.calendarView);

        // Retrieve selected date from Intent or use today's date
        selectedDateKey = getIntent().getStringExtra("selectedDate");
        if (selectedDateKey == null) {
            selectedDateKey = getTodayDateKey();
        }

        // Fetch tasks for the selected date and crop
        List<Task2> taskList = TaskManager.getInstance().getTasksForDate(cropName, selectedDateKey);

        // Initialize TaskAdapter
        taskAdapter = new TaskAdapter(taskList, position -> {
            // Remove the task from TaskManager and update RecyclerView
            TaskManager.getInstance().removeTask(cropName, selectedDateKey, position);
            taskAdapter.updateTasks(TaskManager.getInstance().getTasksForDate(cropName, selectedDateKey));
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
        });
        recyclerViewTasks.setAdapter(taskAdapter);

        // Set a listener for date changes in CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDateKey = year + "-" + (month + 1) + "-" + dayOfMonth; // Months are 0-indexed
            List<Task2> tasksForDate = TaskManager.getInstance().getTasksForDate(cropName, selectedDateKey);
            taskAdapter.updateTasks(tasksForDate);
        });

        // Back button functionality
        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(checkItem.this, SetTask.class);
            backIntent.putExtra("cropName", cropName);
            startActivity(backIntent);
        });


        // Menu button functionality
        ImageButton btnMenu = findViewById(R.id.menuButton);
        btnMenu.setOnClickListener(v -> {
            Intent menuIntent = new Intent(checkItem.this, mainActivity.class);
            startActivity(menuIntent);
        });

        // Initialize Buttons
        Button btnAddTask = findViewById(R.id.btnAddTask);
        Button btnClearTasks = findViewById(R.id.btnClearTasks);

        // Add click listener for Add Task Button
        btnAddTask.setOnClickListener(v -> {
            Intent addTaskIntent = new Intent(checkItem.this, Irrigation_addNewItem.class);
            addTaskIntent.putExtra("cropName", cropName); // Pass cropName
            startActivity(addTaskIntent);
        });

        // Add click listener for Clear All Tasks Button
        // Add click listener for Clear All Tasks Button
        btnClearTasks.setOnClickListener(v -> {
            // Clear tasks for the selected crop
            TaskManager.getInstance().clearTasksForCrop(cropName);

            // Clear events for the selected crop
            EventManager.getInstance().clearEventsForCrop(cropName);

            // Update the adapter with an empty list
            taskAdapter.updateTasks(new ArrayList<>());

            Toast.makeText(this, "All tasks cleared for crop: " + cropName, Toast.LENGTH_SHORT).show();
        });


    }


    // Method to get today's date as a key (yyyy-M-d)
    private String getTodayDateKey() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-indexed
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }
}




