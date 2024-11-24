package com.example.orchardmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Irrigation_addNewItem extends AppCompatActivity {

    private CalendarView calendarView;
    private HashMap<String, String> eventsMap = new HashMap<>(); // To store events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation_addnew);

        // Initialize Views
        calendarView = findViewById(R.id.calendarView);

        // Set up event buttons
        Button sowButton = findViewById(R.id.sowButton);
        Button reapButton = findViewById(R.id.reapButton);
        Button wateringButton = findViewById(R.id.wateringButton);
        Button fertilizeButton = findViewById(R.id.fertilizeButton);

        sowButton.setOnClickListener(v -> showDatePickerDialog("Sow"));
        reapButton.setOnClickListener(v -> showDatePickerDialog("Reap"));
        wateringButton.setOnClickListener(v -> showDayPickerDialog("Watering"));
        fertilizeButton.setOnClickListener(v -> showDayPickerDialog("Fertilize"));

        // Calendar Date Click Listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            String dateKey = getDateKey(calendar);
            if (eventsMap.containsKey(dateKey)) {
                showEventDialog(eventsMap.get(dateKey));
            }
        });
    }

    // Show date picker for sowing and reaping
    private void showDatePickerDialog(String eventType) {
        // Use DatePickerDialog to let user choose a date
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);
                    String dateKey = getDateKey(selectedDate);
                    eventsMap.put(dateKey, eventType);

                    // Mark the date on the calendar
                    markDatesWithEvent();
                },
                year, month, day);

        datePickerDialog.show();
    }

    // Show day picker for watering and fertilizing
    private void showDayPickerDialog(String eventType) {
        final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        boolean[] selectedDays = new boolean[daysOfWeek.length];

        new AlertDialog.Builder(this)
                .setTitle("Select Days for " + eventType)
                .setMultiChoiceItems(daysOfWeek, selectedDays, (dialog, which, isChecked) -> selectedDays[which] = isChecked)
                .setPositiveButton("OK", (dialog, which) -> {
                    for (int i = 0; i < daysOfWeek.length; i++) {
                        if (selectedDays[i]) {
                            List<String> dates = getDatesForDay(daysOfWeek[i]);
                            for (String date : dates) {
                                eventsMap.put(date, eventType);
                            }
                        }
                    }
                    markDatesWithEvent();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

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

    private String getDateKey(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void markDatesWithEvent() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, month, 1);
        while (calendar.get(Calendar.MONTH) == month) {
            String dateKey = getDateKey(calendar);
            if (eventsMap.containsKey(dateKey)) {
                calendarView.setDate(calendar.getTimeInMillis(), true, true);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void showEventDialog(String event) {
        new AlertDialog.Builder(this)
                .setTitle("Event Details")
                .setMessage(event)
                .setPositiveButton("OK", null)
                .show();
    }
}
