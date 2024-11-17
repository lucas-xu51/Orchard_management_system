package com.example.orchardmanagementsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CropDetailActivity extends AppCompatActivity {

    private String cropName; // 作物名称
    private SharedPreferences sharedPreferences; // 用于存储健康状态
    private TextView healthStateTextView; // 健康状态文本

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
        TextView plantingDate = findViewById(R.id.plantingDate);
        healthStateTextView = findViewById(R.id.healthState);
        Button editHealthStateButton = findViewById(R.id.editHealthStateButton);

        // 初始化 SharedPreferences
        sharedPreferences = getSharedPreferences("CropDetails", MODE_PRIVATE);

        // 根据作物名称加载数据
        int cropImageRes = getResources().getIdentifier(cropName.toLowerCase(), "drawable", getPackageName());
        cropImage.setImageResource(cropImageRes);

        tableImage.setImageResource(R.drawable.crop_chart); // 假设表格图片是固定的

        // 从字符串资源文件加载种植日期
        int plantingDateRes = getResources().getIdentifier(cropName.toLowerCase() + "_planting_date", "string", getPackageName());
        plantingDate.setText(getString(R.string.planting_date_label) + ": " + getString(plantingDateRes));

        // 加载保存的健康状态或设置默认值
        String savedHealthState = getSavedHealthState();
        healthStateTextView.setText(getString(R.string.health_state_label) + ": " + savedHealthState);

        // 设置点击 Edit 按钮打开编辑对话框
        editHealthStateButton.setOnClickListener(v -> openEditHealthStateDialog());
    }

    /**
     * 获取保存的健康状态
     */
    private String getSavedHealthState() {
        String healthStateKey = "healthState_" + cropName;
        return sharedPreferences.getString(healthStateKey, "Unknown");
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
     * 保存健康状态到 SharedPreferences
     */
    private void saveHealthState(String healthState) {
        String healthStateKey = "healthState_" + cropName;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(healthStateKey, healthState);
        editor.apply();
    }
}
