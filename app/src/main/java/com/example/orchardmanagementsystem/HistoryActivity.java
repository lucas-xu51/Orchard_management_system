package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    private String cropName;
    private EditText historyEditText;
    private Button editButton, saveButton, cancelButton;
    private SharedPreferences sharedPreferences;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 获取作物名称
        cropName = getIntent().getStringExtra("cropName");

        // 初始化视图
        historyEditText = findViewById(R.id.historyEditText);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // 获取 SharedPreferences
        sharedPreferences = getSharedPreferences("CropHistory", MODE_PRIVATE);

        // 设置返回按钮
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, mainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity after starting MainActivity
        });

        // 加载并显示历史记录
        loadHistory();

        // 设置点击编辑按钮的逻辑
        editButton.setOnClickListener(v -> {
            if (!isEditing) {
                enterEditMode();
            }
        });

        // 设置点击保存按钮的逻辑
        saveButton.setOnClickListener(v -> saveHistoryAndExit());

        // 设置点击取消按钮的逻辑
        cancelButton.setOnClickListener(v -> cancelEdit());
    }

    /**
     * 加载历史记录
     */
    private void loadHistory() {
        String historyKey = "history_" + cropName;
        String savedHistory = sharedPreferences.getString(historyKey, "");
        historyEditText.setText(savedHistory);
    }

    /**
     * 进入编辑模式
     */
    private void enterEditMode() {
        // 隐藏编辑按钮并显示保存和取消按钮
        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);

        // 启用编辑模式
        historyEditText.setEnabled(true);

        // 切换为编辑状态
        isEditing = true;
    }

    /**
     * 保存历史记录并退出编辑模式
     */
    private void saveHistoryAndExit() {
        String updatedHistory = historyEditText.getText().toString().trim();

        saveHistory(updatedHistory);
        Toast.makeText(this, "History saved for " + cropName, Toast.LENGTH_SHORT).show();
        exitEditMode();  // 退出编辑模式

    }

    /**
     * 取消编辑，恢复原始历史记录并退出编辑模式
     */
    private void cancelEdit() {
        loadHistory();  // 恢复原始历史记录
        exitEditMode();  // 退出编辑模式
    }

    /**
     * 退出编辑模式，恢复显示模式
     */
    private void exitEditMode() {
        // 恢复为非编辑状态，隐藏保存和取消按钮，显示编辑按钮
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        // 禁用编辑框
        historyEditText.setEnabled(false);

        // 切换回非编辑状态
        isEditing = false;
    }

    /**
     * 保存历史记录到 SharedPreferences
     */
    private void saveHistory(String history) {
        String historyKey = "history_" + cropName;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(historyKey, history);
        editor.apply();
    }
}
