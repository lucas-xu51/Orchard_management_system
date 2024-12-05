package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class inventory_seed extends AppCompatActivity {

    private int currentQuantity; // 当前数量
    private String itemName; // 项目名称
    private TextView quantityTextView; // 数量的 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_seed);

        // 接收传递的数据
        itemName = getIntent().getStringExtra("item_name");

        // 从SharedPreferences获取当前数量
        SharedPreferences sharedPreferences = getSharedPreferences("inventory_data", MODE_PRIVATE);
        currentQuantity = sharedPreferences.getInt(itemName, 0); // 获取当前数量

        // 绑定视图
        TextView nameTextView = findViewById(R.id.itemName);
        quantityTextView = findViewById(R.id.quantity);
        Button updateButton = findViewById(R.id.updateButton);
        Button confirmButton = findViewById(R.id.confirmButton);
        ImageView backButton = findViewById(R.id.backButton);

        // 显示当前数据
        nameTextView.setText("Item: " + itemName);
        quantityTextView.setText("Quantity: " + currentQuantity);

        // 返回按钮逻辑
        backButton.setOnClickListener(v -> finish());

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_seed.this, mainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity after starting MainActivity
        });

        // 更新按钮逻辑
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_seed.this, inventory_update.class);
            intent.putExtra("item_name", itemName);
            startActivityForResult(intent, 1); // 跳转到更新页面
        });

        // 确认按钮逻辑
        confirmButton.setOnClickListener(v -> {
            // 返回更新的数据给上一页
            Intent resultIntent = new Intent();
            resultIntent.putExtra("item_name", itemName);
            resultIntent.putExtra("updated_quantity", currentQuantity);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Confirm Quantity: " + currentQuantity, Toast.LENGTH_SHORT).show();
            finish(); // 关闭当前页面
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) { // 判断返回的数据是否来自更新页面
            if (resultCode == RESULT_OK && data != null) {
                // 获取更新后的数量
                currentQuantity = data.getIntExtra("updated_quantity", currentQuantity);

                // 更新显示
                quantityTextView.setText("Quantity: " + currentQuantity);

                // 保存更新的数量到SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("inventory_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(itemName, currentQuantity);
                editor.apply(); // 提交更新

                Toast.makeText(this, "Updated Quantity: " + currentQuantity, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Update canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

