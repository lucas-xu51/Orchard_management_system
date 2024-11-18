package com.example.orchardmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CropDetailActivity extends AppCompatActivity {

    private String cropName; // 作物名称
    private SharedPreferences sharedPreferences; // 用于存储健康状态和种植日期
    private TextView healthStateTextView; // 健康状态文本
    private TextView plantingDateTextView; // 种植日期文本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_detail_activity);

        // 获取传递的作物名称
        cropName = getIntent().getStringExtra("cropName");

        // 设置顶部返回按钮
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // 初始化视图
        ImageView cropImage = findViewById(R.id.cropImage);
        ImageView tableImage = findViewById(R.id.tableImage);
        plantingDateTextView = findViewById(R.id.plantingDate);
        healthStateTextView = findViewById(R.id.healthState);
        Button editPlantingDateButton = findViewById(R.id.editPlantingDateButton);
        Button editHealthStateButton = findViewById(R.id.editHealthStateButton);

        // 初始化 SharedPreferences
        sharedPreferences = getSharedPreferences("CropDetails", MODE_PRIVATE);

        // 根据作物名称加载数据
        int cropImageRes = getResources().getIdentifier(cropName.toLowerCase(), "drawable", getPackageName());
        cropImage.setImageResource(cropImageRes);

        tableImage.setImageResource(R.drawable.crop_chart); // 假设表格图片是固定的

        // 加载保存的种植日期或设置默认值
        String savedPlantingDate = getSavedPlantingDate();
        plantingDateTextView.setText(getString(R.string.planting_date_label) + ": " + savedPlantingDate);

        // 加载保存的健康状态或设置默认值
        String savedHealthState = getSavedHealthState();
        healthStateTextView.setText(getString(R.string.health_state_label) + ": " + savedHealthState);

        // 设置点击 Edit 按钮打开日期选择器
        editPlantingDateButton.setOnClickListener(v -> openDatePickerDialog());

        // 设置点击 Edit 按钮打开编辑健康状态对话框
        editHealthStateButton.setOnClickListener(v -> openEditHealthStateDialog());

        Button demandInformationButton = findViewById(R.id.demandInfoButton);
        demandInformationButton.setOnClickListener(v -> {
            // 创建 Intent 跳转到 DemandInformationActivity
            Intent intent = new Intent(CropDetailActivity.this, DemandInformationActivity.class);
            intent.putExtra("cropName", cropName);
            startActivity(intent);
        });

        Button historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(CropDetailActivity.this, HistoryActivity.class);
            intent.putExtra("cropName", cropName);
            startActivity(intent);
        });
    }

    /**
     * 获取保存的健康状态
     */
    private String getSavedHealthState() {
        String healthStateKey = "healthState_" + cropName;
        return sharedPreferences.getString(healthStateKey, "Unknown");
    }

    /**
     * 获取保存的种植日期
     */
    private String getSavedPlantingDate() {
        String plantingDateKey = "plantingDate_" + cropName;
        return sharedPreferences.getString(plantingDateKey, "Not set");
    }

    /**
     * 打开编辑健康状态的对话框
     */
    private void openEditHealthStateDialog() {
        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Health State");

        // 创建输入框
        final EditText input = new EditText(this);
        String currentHealthState = healthStateTextView.getText().toString()
                .replace(getString(R.string.health_state_label) + ": ", "");
        input.setText(currentHealthState);
        builder.setView(input);

        // 添加保存按钮
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newHealthState = input.getText().toString().trim();
            if (!newHealthState.isEmpty()) {
                saveHealthState(newHealthState);
                healthStateTextView.setText(getString(R.string.health_state_label) + ": " + newHealthState);
                Toast.makeText(this, "Health State updated for " + cropName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Health State cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        // 添加取消按钮
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /**
     * 打开日期选择器对话框
     */
    private void openDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        // 获取当前日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建日期选择器
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // 格式化日期
            String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            savePlantingDate(selectedDate);
            plantingDateTextView.setText(getString(R.string.planting_date_label) + ": " + selectedDate);
            Toast.makeText(this, "Planting Date updated for " + cropName, Toast.LENGTH_SHORT).show();
        }, year, month, day);

        datePickerDialog.show();
    }

    /**
     * 保存健康状态到 SharedPreferences
     */
    private void saveHealthState(String healthState) {
        String healthStateKey = "healthState_" + cropName;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(healthStateKey, healthState);
        editor.apply();
    }

    /**
     * 保存种植日期到 SharedPreferences
     */
    private void savePlantingDate(String plantingDate) {
        String plantingDateKey = "plantingDate_" + cropName;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(plantingDateKey, plantingDate);
        editor.apply();
    }
}
