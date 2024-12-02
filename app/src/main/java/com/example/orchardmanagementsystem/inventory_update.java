package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class inventory_update extends AppCompatActivity {

    private String itemName;
    private int currentQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update);

        // 设置返回按钮
        ImageView backButton1 = findViewById(R.id.backButton);
        backButton1.setOnClickListener(v -> finish());

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_update.this, mainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity after starting MainActivity
        });

        // 获取传递的数据
        itemName = getIntent().getStringExtra("item_name");
        currentQuantity = getIntent().getIntExtra("item_quantity", 0);

        // 初始化视图
        TextView itemNameTextView = findViewById(R.id.itemName);
        TextView currentQuantityTextView = findViewById(R.id.currentQuantity);
        EditText newQuantityEditText = findViewById(R.id.newQuantity);
        Button confirmButton = findViewById(R.id.update);
        Button backButton = findViewById(R.id.update2);

        // 显示项目名称和当前数量
        itemNameTextView.setText("Update Quantity for: " + itemName);
        currentQuantityTextView.setText("Current Quantity: " + currentQuantity);

        // 确认按钮逻辑
        confirmButton.setOnClickListener(v -> {
            String newQuantityString = newQuantityEditText.getText().toString();

            if (!newQuantityString.isEmpty()) {
                int updatedQuantity = Integer.parseInt(newQuantityString);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_quantity", updatedQuantity);
                setResult(RESULT_OK, resultIntent);

                Toast.makeText(this, "Quantity updated to: " + updatedQuantity, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            }
        });

        // 返回按钮逻辑
        backButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "Update canceled by user", Toast.LENGTH_SHORT).show();
            finish();
        });

        ImageView warehouseButton = findViewById(R.id.warehouse);
        warehouseButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_update.this, Inventory_Management.class);
            startActivity(intent);
            finish(); // 关闭当前页面
        });
    }

}
