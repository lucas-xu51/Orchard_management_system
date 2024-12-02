package com.example.orchardmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Irrigation_addNewItem extends AppCompatActivity {

    private CalendarView calendarView;
    private String cropName;
    HashMap<String, HashMap<String, List<Event>>> eventsMap = EventManager.getInstance().getEventsMap();;

    @Override
    protected void onResume() {
        super.onResume();
        markDatesWithEvent(); // Refresh the calendar view
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation_addnew);

        Intent intent = getIntent();
        cropName = intent.getStringExtra("cropName");

        // Initialize Views
        calendarView = findViewById(R.id.calendarView);

        // Set calendar date change listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            String dateKey = getDateKey(calendar);

            // Retrieve the events map and check if events exist for the selected crop and date
            HashMap<String, HashMap<String, List<Event>>> eventsMap = EventManager.getInstance().getEventsMap();
            if (eventsMap.containsKey(cropName) && eventsMap.get(cropName).containsKey(dateKey)) {
                showEventDialog(dateKey);  // If events exist, show the event dialog
            } else {
                Log.d("Irrigation_addNewItem", "No events for this date.");
            }
        });

        // Set up navigation buttons
        ImageButton btnBack = findViewById(R.id.backButton);
        ImageButton btnMenu = findViewById(R.id.menuButton);


        btnBack.setOnClickListener(v -> finish());

        btnMenu.setOnClickListener(v -> {
            Intent menuIntent = new Intent(Irrigation_addNewItem.this, mainActivity.class);
            startActivity(menuIntent);
        });

        // Set up event buttons
        Button sowButton = findViewById(R.id.sowButton);
        Button reapButton = findViewById(R.id.reapButton);
        Button wateringButton = findViewById(R.id.wateringButton);
        Button fertilizeButton = findViewById(R.id.fertilizeButton);

        sowButton.setOnClickListener(v -> showDatePickerDialog("Sow"));
        reapButton.setOnClickListener(v -> showDatePickerDialog("Reap"));
        wateringButton.setOnClickListener(v -> showWateringOrFertilizingDialog("Watering"));
        fertilizeButton.setOnClickListener(v -> showWateringOrFertilizingDialog("Fertilize"));
    }

    // Show date picker for sowing and reaping
    private void showDatePickerDialog(String eventType) {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);
                    String dateKey = getDateKey(selectedDate);

                    // Add the event to the date's event list
                    Event event = new Event(eventType, "");
                    addEventToDate(dateKey, event);
                },
                year, month, day);

        datePickerDialog.show();
    }

    // Show dialog for watering or fertilizing with the same detail for all selected days
    private void showWateringOrFertilizingDialog(String eventType) {
        final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        boolean[] selectedDays = new boolean[daysOfWeek.length];

        new AlertDialog.Builder(this)
                .setTitle("Select Days for " + eventType)
                .setMultiChoiceItems(daysOfWeek, selectedDays, (dialog, which, isChecked) -> selectedDays[which] = isChecked)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Collect selected days
                    List<String> selectedDates = new ArrayList<>();
                    for (int i = 0; i < daysOfWeek.length; i++) {
                        if (selectedDays[i]) {
                            selectedDates.addAll(getDatesForDay(daysOfWeek[i]));
                        }
                    }
                    if (!selectedDates.isEmpty()) {
                        showCommentDialog(eventType, selectedDates);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Retrieve all dates for a given day of the week
    private List<String> getDatesForDay(String dayName) {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, month, 1);
        while (calendar.get(Calendar.MONTH) == month) {
            if (calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, getResources().getConfiguration().locale).equals(dayName)) {
                dates.add(getDateKey(calendar));
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    // Convert calendar date to string format (YYYY-MM-DD)
    private String getDateKey(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Refresh the calendar to mark dates with events
    private void markDatesWithEvent() {
        HashMap<String, HashMap<String, List<Event>>> eventsMap = EventManager.getInstance().getEventsMap();
        HashMap<String, List<Event>> cropEventsMap = eventsMap.get(cropName);
        if (cropEventsMap == null) return;

        // Additional code can go here to visually mark dates with events (e.g., with a special background or marker)
    }

    // Show input dialog for adding a comment to watering or fertilizing
    private void showCommentDialog(String eventType, List<String> dates) {
        final EditText input = new EditText(this);
        input.setHint("Enter details (e.g., time, type of fertilizer)");

        new AlertDialog.Builder(this)
                .setTitle("Add Comment for " + eventType)
                .setMessage("Enter details for " + eventType)
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String comment = input.getText().toString();
                    for (String dateKey : dates) {
                        Event event = new Event(eventType, comment);
                        addEventToDate(dateKey, event);
                    }
                    markDatesWithEvent();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Add event to the date's event list
    private void addEventToDate(String dateKey, Event event) {
        HashMap<String, HashMap<String, List<Event>>> eventsMap = EventManager.getInstance().getEventsMap();
        HashMap<String, List<Event>> cropEventsMap = eventsMap.getOrDefault(cropName, new HashMap<>());

        List<Event> eventsList = cropEventsMap.getOrDefault(dateKey, new ArrayList<>());
        eventsList.add(event);
        cropEventsMap.put(dateKey, eventsList);
        eventsMap.put(cropName, cropEventsMap);

        // Add to TaskManager with cropName
        Task2 task = new Task2(event.getEventType(), event.getComment());
        TaskManager.getInstance().addTask(cropName, dateKey, task);

        markDatesWithEvent();  // Refresh marked dates on calendar
    }

    // Show event dialog to display event details for a selected date
    private void showEventDialog(String dateKey) {
        HashMap<String, HashMap<String, List<Event>>> eventsMap = EventManager.getInstance().getEventsMap();
        HashMap<String, List<Event>> cropEventsMap = eventsMap.get(cropName);

        if (cropEventsMap != null && cropEventsMap.containsKey(dateKey)) {
            List<Event> events = cropEventsMap.get(dateKey);
            StringBuilder eventsDetails = new StringBuilder();
            for (Event event : events) {
                eventsDetails.append(event.getEventType()).append(": ").append(event.getComment()).append("\n");
            }

            new AlertDialog.Builder(this)
                    .setTitle("Event Details for " + dateKey)
                    .setMessage(eventsDetails.toString())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    // Event class to store event type and comment
    static class Event {
        private final String eventType;
        private final String comment;

        public Event(String eventType, String comment) {
            this.eventType = eventType;
            this.comment = comment;
        }

        public String getEventType() {
            return eventType;
        }

        public String getComment() {
            return comment;
        }
    }
}
