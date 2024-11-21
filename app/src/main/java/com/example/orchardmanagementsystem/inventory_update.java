package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class inventory_update extends AppCompatActivity {

    private String itemName; // 当前项目名称
    private int currentQuantity; // 当前数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update);

        // 接收传递的数据
        itemName = getIntent().getStringExtra("item_name");
        currentQuantity = getIntent().getIntExtra("item_quantity", 0);

        // 显示项目名称
        TextView nameTextView = findViewById(R.id.newname);
        nameTextView.setText("Update Quantity for: " + itemName);

        // Confirm 按钮逻辑
        Button confirmButton = findViewById(R.id.update);
        confirmButton.setOnClickListener(v -> {
            EditText quantityEditText = findViewById(R.id.enter);
            String newQuantityString = quantityEditText.getText().toString();

            if (!newQuantityString.isEmpty()) {
                int updatedQuantity = Integer.parseInt(newQuantityString);

                // 创建 Intent 跳转回 inventory_screen2
                Intent intent = new Intent(inventory_update.this, inventory_screen2.class);
                intent.putExtra("item_name", itemName); // 传递更新的项目名称
                intent.putExtra("updated_quantity", updatedQuantity); // 传递更新的数量
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 清除顶部的 Activity 栈
                startActivity(intent); // 跳转到 inventory_screen2
            }
        });
    }
}
