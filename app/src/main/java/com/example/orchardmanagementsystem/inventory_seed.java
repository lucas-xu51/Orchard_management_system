package com.example.orchardmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class inventory_seed extends AppCompatActivity {

    private int currentQuantity; // 当前数量
    private String itemName; // 项目名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_seed);
        EdgeToEdge.enable(this);

        // 接收传递的数据
        itemName = getIntent().getStringExtra("item_name");
        currentQuantity = getIntent().getIntExtra("item_quantity", 0);

        // 显示项目名称和数量
        TextView nameTextView = findViewById(R.id.newname); // 项目名称的 TextView
        TextView quantityTextView = findViewById(R.id.enter); // 项目数量的 TextView

        nameTextView.setText("Item: " + itemName);
        quantityTextView.setText("Quantity: " + currentQuantity);

        // 返回按钮逻辑
        ImageView backButton = findViewById(R.id.imageView4); // 替换为您的返回按钮 ID
        backButton.setOnClickListener(v -> finish()); // 关闭当前页面，返回上一页面

        // Confirm 按钮逻辑
        Button confirmButton = findViewById(R.id.Confirm); // 替换为 Confirm 按钮的实际 ID
        confirmButton.setOnClickListener(v -> finish()); // 关闭当前页面

        // Update 按钮逻辑
        Button updateButton = findViewById(R.id.Update); // 替换为 Update 按钮的实际 ID
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_seed.this, inventory_update.class);
            intent.putExtra("item_name", itemName);
            intent.putExtra("item_quantity", currentQuantity);
            startActivityForResult(intent, 1); // 跳转到更新页面，请求码为 1
        });
    }

    // 接收来自 UpdateQuantityActivity 的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            currentQuantity = data.getIntExtra("updated_quantity", currentQuantity); // 更新数量

            // 将更新后的数据传递回 inventory_screen2
            Intent intent = new Intent(this, inventory_screen2.class);
            intent.putExtra("item_name", itemName);
            intent.putExtra("updated_quantity", currentQuantity);
            startActivity(intent); // 跳转回 inventory_screen2
            finish(); // 关闭当前页面
        }
    }
}
