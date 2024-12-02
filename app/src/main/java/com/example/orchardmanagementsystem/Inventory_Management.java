package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class Inventory_Management extends AppCompatActivity {

    // 公用数据源，用于存储作物的最新数量
    public static final HashMap<String, Integer> cropQuantities = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory_management);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化默认数据
        initializeDefaultQuantities();

        // 绑定 toggleListMode 按钮的点击事件
        Button toggleListModeButton = findViewById(R.id.menu);
        toggleListModeButton.setOnClickListener(v -> {
            // 启动新的 Activity（inventory_screen2）
            Intent intent = new Intent(Inventory_Management.this, inventory_screen2.class);
            startActivity(intent);
        });

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory_Management.this, mainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity after starting MainActivity
        });

        // 为每个作物设置点击事件
        setCropClickListener(R.id.Tomato, "Tomato");
        setCropClickListener(R.id.Potato, "Potato");
        setCropClickListener(R.id.Apple, "Apple");
        setCropClickListener(R.id.Cabbage, "Cabbage");
        setCropClickListener(R.id.Carrot, "Carrot");
        setCropClickListener(R.id.Pineapple, "Pineapple");
        setCropClickListener(R.id.Shovel, "Shovel");
        setCropClickListener(R.id.Hoe, "Hoe");
        setCropClickListener(R.id.fertilizer, "Fertilizer");
        setCropClickListener(R.id.Pesticides, "Pesticide");

        // 设置返回按钮
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        ImageButton btnCrop = findViewById(R.id.crop);
        ImageButton btnWarehouse = findViewById(R.id.warehouse);
        ImageButton btnCalendar = findViewById(R.id.calendar);
        ImageButton btnMap = findViewById(R.id.map);

        btnCrop.setOnClickListener(v -> {
            // 跳转到 Crop Monitoring 页面
            Intent cropIntent = new Intent(Inventory_Management.this, crop_monitoring_Activity.class);
            startActivity(cropIntent);
        });



        btnCalendar.setOnClickListener(v -> {
            // 跳转到 Task 页面
            Intent calendarIntent = new Intent(Inventory_Management.this, Task.class);
            startActivity(calendarIntent);
        });

        btnMap.setOnClickListener(v -> {
            // 跳转到 LocationFarmActivity 页面
            Intent mapIntent = new Intent(Inventory_Management.this, LocationFarmActivity.class);
            startActivity(mapIntent);
        });

    }

    private void setCropClickListener(int imageViewId, String cropName) {
        ImageView imageView = findViewById(imageViewId);
        imageView.setOnClickListener(v -> {
            // 获取最新的数量
            int currentQuantity = cropQuantities.getOrDefault(cropName, 0);
            Intent intent = new Intent(Inventory_Management.this, inventory_seed.class);
            intent.putExtra("item_name", cropName);
            intent.putExtra("item_quantity", currentQuantity);
            startActivityForResult(intent, 1); // 使用 startActivityForResult 跳转
        });
    }

    // 初始化默认作物数量
    private void initializeDefaultQuantities() {
        cropQuantities.put("Tomato", 1);
        cropQuantities.put("Potato", 5);
        cropQuantities.put("Apple", 3);
        cropQuantities.put("Cabbage", 2);
        cropQuantities.put("Carrot", 4);
        cropQuantities.put("Pineapple", 6);
        cropQuantities.put("Shovel", 8);
        cropQuantities.put("Hoe", 10);
        cropQuantities.put("Fertilizer", 14);
        cropQuantities.put("Pesticide", 16);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // 接收返回的更新数据
            String itemName = data.getStringExtra("item_name");
            int updatedQuantity = data.getIntExtra("updated_quantity", -1);

            if (itemName != null && updatedQuantity != -1) {
                // 更新全局共享数据源 cropQuantities
                cropQuantities.put(itemName, updatedQuantity);
            }
        }

}
}
