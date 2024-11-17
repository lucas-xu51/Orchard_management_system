package com.example.orchardmanagementsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CropDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_detail_activity);

        // 获取传递的作物名称
        String cropName = getIntent().getStringExtra("cropName");

        // 设置顶部返回按钮
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // 获取布局中的视图
        ImageView cropImage = findViewById(R.id.cropImage);
        ImageView tableImage = findViewById(R.id.tableImage);
        TextView plantingDate = findViewById(R.id.plantingDate);
        TextView healthState = findViewById(R.id.healthState);

        // 根据作物名称加载数据
        int cropImageRes = getResources().getIdentifier(cropName.toLowerCase(), "drawable", getPackageName());
        cropImage.setImageResource(cropImageRes);

        tableImage.setImageResource(R.drawable.crop_chart); // 假设表格图片是固定的

        // 从字符串资源文件加载种植日期和健康状态
        int plantingDateRes = getResources().getIdentifier(cropName.toLowerCase() + "_planting_date", "string", getPackageName());
        int healthStateRes = getResources().getIdentifier(cropName.toLowerCase() + "_health_state", "string", getPackageName());

        plantingDate.setText(getString(R.string.planting_date_label) + ": " + getString(plantingDateRes));
        healthState.setText(getString(R.string.health_state_label) + ": " + getString(healthStateRes));
    }
}