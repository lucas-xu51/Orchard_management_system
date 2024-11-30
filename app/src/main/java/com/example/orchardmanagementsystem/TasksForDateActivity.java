package com.example.orchardmanagementsystem;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class TasksForDateActivity extends AppCompatActivity {

    private List<String> taskList = new ArrayList<>();
    private LinearLayout taskListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_for_date);

        String selectedDate = getIntent().getStringExtra("selectedDate");

        TextView dateTitle = findViewById(R.id.dateTitle);
        taskListContainer = findViewById(R.id.taskListContainer);
        Button addTaskButton = findViewById(R.id.addTaskButton);
        Button clearTasksButton = findViewById(R.id.clearTasksButton);

        // Set the date title
        dateTitle.setText("Tasks for " + selectedDate);

        // Add task
        addTaskButton.setOnClickListener(v -> showAddTaskDialog());

        // Clear tasks
        clearTasksButton.setOnClickListener(v -> {
            taskList.clear();
            updateTaskList();
        });
    }

    private void showAddTaskDialog() {
        final EditText taskInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setMessage("Enter task description:")
                .setView(taskInput)
                .setPositiveButton("Add", (dialog, which) -> {
                    String task = taskInput.getText().toString();
                    if (!task.isEmpty()) {
                        taskList.add(task);
                        updateTaskList();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateTaskList() {
        taskListContainer.removeAllViews();
        for (String task : taskList) {
            TextView taskView = new TextView(this);
            taskView.setText(task);
            taskView.setPadding(8, 8, 8, 8);
            taskListContainer.addView(taskView);
        }
    }
}
