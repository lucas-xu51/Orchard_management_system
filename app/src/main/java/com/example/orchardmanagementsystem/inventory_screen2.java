package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

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

        // 接收来自 inventory_update 的更新数据
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("item_name");
        int updatedQuantity = intent.getIntExtra("updated_quantity", -1);

        if (itemName != null && updatedQuantity != -1) {
            itemData.put(itemName, updatedQuantity); // 更新数量
        }

        // 设置返回按钮
        ImageView backButton = findViewById(R.id.imageView2); // 替换为您的返回按钮 ID
        backButton.setOnClickListener(v -> finish());

        // 设置每个 LinearLayout 的点击事件
        setupItemClickListeners();
    }

    private void setupItemClickListeners() {
        setupClickListener(R.id.tomato_layout, "Tomato");
        setupClickListener(R.id.potato_layout, "Potato");
        setupClickListener(R.id.apple_layout, "Apple");
        setupClickListener(R.id.cabbage_layout, "Cabbage");
        setupClickListener(R.id.carrot_layout, "Carrot");
        setupClickListener(R.id.pineapple_layout, "Pineapple");
    }

    private void setupClickListener(int layoutId, String itemName) {
        LinearLayout layout = findViewById(layoutId);
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_screen2.this, inventory_seed.class);
            intent.putExtra("item_name", itemName);
            intent.putExtra("item_quantity", itemData.getOrDefault(itemName, 0));
            startActivity(intent);
        });
    }
}
