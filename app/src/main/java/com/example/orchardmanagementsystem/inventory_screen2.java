package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class inventory_screen2 extends AppCompatActivity {
    private HashMap<String, Integer> itemData; // 存储项目名称和数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_screen2);
        EdgeToEdge.enable(this);

        // 初始化 itemData
        itemData = new HashMap<>();
        itemData.put("Tomato", 1);
        itemData.put("Potato", 5);
        itemData.put("Apple", 3);
        itemData.put("Cabbage", 2);
        itemData.put("Carrot", 4);
        itemData.put("Pineapple", 6);
        itemData.put("Shovel", 8);
        itemData.put("Hoe", 10);
        itemData.put("Fertilizer", 14);
        itemData.put("Pesticide", 16);

        // 设置返回按钮
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_screen2.this, mainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity after starting MainActivity
        });

        // 设置每个 LinearLayout 的点击事件
        setupItemClickListeners();
        loadCropQuantities();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCropQuantities(); // 在每次页面可见时加载最新数据
    }

    private void loadCropQuantities() {
        // 遍历 Inventory_Management.cropQuantities 并显示数据
        for (Map.Entry<String, Integer> entry : Inventory_Management.cropQuantities.entrySet()) {
            String cropName = entry.getKey();
            int quantity = entry.getValue();

            // 动态更新 TextView，假设每个作物有对应的 TextView ID
            int textViewId = getResources().getIdentifier(cropName.toLowerCase() + "_quantity", "id", getPackageName());
            TextView quantityTextView = findViewById(textViewId);

            if (quantityTextView != null) {
                quantityTextView.setText("Quantity: " + quantity);
            }
        }
    }


    private void setupItemClickListeners() {
        setupClickListener(R.id.tomato_layout, "Tomato");
        setupClickListener(R.id.potato_layout, "Potato");
        setupClickListener(R.id.apple_layout, "Apple");
        setupClickListener(R.id.cabbage_layout, "Cabbage");
        setupClickListener(R.id.carrot_layout, "Carrot");
        setupClickListener(R.id.pineapple_layout, "Pineapple");
        setupClickListener(R.id.tool1, "Shovel");
        setupClickListener(R.id.tool2, "Hoe");
        setupClickListener(R.id.fertilizer, "Fertilizer");
        setupClickListener(R.id.pesticide, "Pesticide");
    }

    private void setupClickListener(int layoutId, String itemName) {
        LinearLayout layout = findViewById(layoutId);
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_screen2.this, inventory_seed.class);
            intent.putExtra("item_name", itemName);
            intent.putExtra("item_quantity", itemData.getOrDefault(itemName, 0));
            startActivityForResult(intent, 1); // 请求码为 1
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // 接收来自 inventory_seed 的更新数据
            String itemName = data.getStringExtra("item_name");
            int updatedQuantity = data.getIntExtra("updated_quantity", -1);

            if (itemName != null && updatedQuantity != -1) {
                // 更新 itemData
                itemData.put(itemName, updatedQuantity);

            }
        }

    }}
