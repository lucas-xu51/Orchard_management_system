package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SetTask extends AppCompatActivity {

    private String selectedDate;
    private String cropName;
    private ImageView cropImageView;
    private HashMap<String, HashMap<String, List<Irrigation_addNewItem.Event>>> eventsMap = EventManager.getInstance().getEventsMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_task);

        // Retrieve the crop name from Intent
        Intent intent = getIntent();
        cropName = intent.getStringExtra("cropName");

        // Initialize the ImageView
        cropImageView = findViewById(R.id.cropImage);

        // Set the crop image based on the crop name
        setCropImage(cropName);

        CalendarView calendarView = findViewById(R.id.calendarView);
        Button checkItemsButton = findViewById(R.id.checkItemsButton);

        // Initialize selectedDate to today's date
        Calendar calendar = Calendar.getInstance();
        selectedDate = calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);

        // Update selectedDate when the user picks a new date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

            // Show events for the selected date
            showEventDialog(selectedDate);
        });

        // Navigate to the "Tasks for Date" screen
        checkItemsButton.setOnClickListener(v -> {
            Intent checkItemsIntent = new Intent(SetTask.this, checkItem.class);
            checkItemsIntent.putExtra("selectedDate", selectedDate);
            checkItemsIntent.putExtra("cropName", cropName);
            startActivity(checkItemsIntent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        ImageButton btnMenu = findViewById(R.id.menuButton);

        // Add click listener for Back Button
        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(SetTask.this, Task.class);
            startActivity(backIntent);
        });

        // Add click listener for Menu Button
        btnMenu.setOnClickListener(v -> {
            Intent menuIntent = new Intent(SetTask.this, mainActivity.class);
            startActivity(menuIntent);
        });
    }

    private void setCropImage(String cropName) {
        if (cropName == null) {
            return; // No crop name provided
        }

        int imageResId = 0;
        switch (cropName.toLowerCase()) {
            case "tomato":
                imageResId = R.drawable.tomato;
                break;
            case "potato":
                imageResId = R.drawable.potato;
                break;
            case "apple":
                imageResId = R.drawable.apple;
                break;
            case "cabbage":
                imageResId = R.drawable.cabbage;
                break;
            case "carrot":
                imageResId = R.drawable.carrot;
                break;
            case "pineapple":
                imageResId = R.drawable.pineapple;
                break;
        }

        // Set the image resource
        cropImageView.setImageResource(imageResId);
    }

    // Show event dialog for the selected date
    // Show event dialog for the selected date
    private void showEventDialog(String dateKey) {
        HashMap<String, List<Irrigation_addNewItem.Event>> cropEventsMap = eventsMap.get(cropName);

        if (cropEventsMap != null && cropEventsMap.containsKey(dateKey)) {
            List<Irrigation_addNewItem.Event> events = cropEventsMap.get(dateKey);
            StringBuilder eventsDetails = new StringBuilder();
            for (Irrigation_addNewItem.Event event : events) {
                eventsDetails.append(event.getEventType()).append(": ").append(event.getComment()).append("\n");
            }

            // Show the event details in a dialog
            new AlertDialog.Builder(this)
                    .setTitle("Event Details for " + dateKey)
                    .setMessage(eventsDetails.toString())
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            // Log that there are no events for debugging, but don't show a dialog
            Log.d("SetTask", "No events for this date: " + dateKey);
        }
    }
}
