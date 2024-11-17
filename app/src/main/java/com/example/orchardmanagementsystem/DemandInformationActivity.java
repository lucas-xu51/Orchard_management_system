package com.example.orchardmanagementsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DemandInformationActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private LinearLayout infoListLayout;
    private String cropName; // 当前作物的唯一标识符
    private static final String INFO_PREFS = "DemandInfoPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demand_information_activity);

        // 获取当前作物的唯一标识符
        cropName = getIntent().getStringExtra("cropName");
        if (cropName == null || cropName.isEmpty()) {
            Toast.makeText(this, "Crop ID is missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 初始化组件
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton addInfoButton = findViewById(R.id.addInfoButton);
        infoListLayout = findViewById(R.id.infoList);

        // 初始化 SharedPreferences
        sharedPreferences = getSharedPreferences(INFO_PREFS, MODE_PRIVATE);

        // 加载该作物的需求信息
        loadSavedInfo();

        // 返回按钮
        backButton.setOnClickListener(v -> finish());

        // 添加信息按钮
        addInfoButton.setOnClickListener(v -> openAddInfoDialog());
    }

    /**
     * 加载当前作物的需求信息
     */
    private void loadSavedInfo() {
        infoListLayout.removeAllViews();

        int infoCount = sharedPreferences.getInt(cropName + "_info_count", 0);
        for (int i = 0; i < infoCount; i++) {
            String info = sharedPreferences.getString(cropName + "_info_" + i, "");
            addInfoToList(info, i);
        }
    }

    /**
     * 添加一条信息到列表
     */
    private void addInfoToList(String info, int position) {
        View itemView = getLayoutInflater().inflate(R.layout.item_demand_info, null);
        TextView infoText = itemView.findViewById(R.id.infoText);
        ImageButton editButton = itemView.findViewById(R.id.editButton);
        ImageButton deleteButton = itemView.findViewById(R.id.deleteButton);

        infoText.setText(info);

        // 编辑按钮
        editButton.setOnClickListener(v -> openEditInfoDialog(info, position));

        // 删除按钮
        deleteButton.setOnClickListener(v -> deleteInfo(position));

        infoListLayout.addView(itemView, 0);
    }

    /**
     * 打开添加信息的对话框
     */
    private void openAddInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Demand Information");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newInfo = input.getText().toString().trim();
            if (!newInfo.isEmpty()) {
                saveInfo(newInfo);
                loadSavedInfo();
                Toast.makeText(this, "Information added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Information cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /**
     * 打开编辑信息的对话框
     */
    private void openEditInfoDialog(String currentInfo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Demand Information");

        final EditText input = new EditText(this);
        input.setText(currentInfo);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedInfo = input.getText().toString().trim();
            if (!updatedInfo.isEmpty()) {
                saveInfo(updatedInfo, position);
                loadSavedInfo();
                Toast.makeText(this, "Information updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Information cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /**
     * 删除一条信息
     */
    private void deleteInfo(int position) {
        int infoCount = sharedPreferences.getInt(cropName + "_info_count", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = position; i < infoCount - 1; i++) {
            String nextInfo = sharedPreferences.getString(cropName + "_info_" + (i + 1), "");
            editor.putString(cropName + "_info_" + i, nextInfo);
        }

        editor.remove(cropName + "_info_" + (infoCount - 1));
        editor.putInt(cropName + "_info_count", infoCount - 1);
        editor.apply();

        loadSavedInfo();
    }

    /**
     * 保存新的信息
     */
    private void saveInfo(String info) {
        int infoCount = sharedPreferences.getInt(cropName + "_info_count", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cropName + "_info_" + infoCount, info);
        editor.putInt(cropName + "_info_count", infoCount + 1);
        editor.apply();
    }

    /**
     * 保存编辑后的信息
     */
    private void saveInfo(String info, int position) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cropName + "_info_" + position, info);
        editor.apply();
    }
}
