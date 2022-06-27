package com.example.cs360blazehaldermanprojecttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    EditText item_name_input, item_quantity_input;
    Button add_item_button;
    DBHelper userDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        item_name_input = findViewById(R.id.editItemName);
        item_quantity_input = findViewById(R.id.editItemQuantity);
        add_item_button = findViewById(R.id.add_item_button);

        SessionManagement sessionManagement = LoginActivity.sessionManagement;
        String user_id = sessionManagement.getSession();
        userDB = new DBHelper(this);
        String username = userDB.getUsername(user_id);

        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(AddItemActivity.this);
                String user_id = sessionManagement.getSession();
                InventoryDBHelper myDB = new InventoryDBHelper(AddItemActivity.this);
                myDB.addInventoryItem(item_name_input.getText().toString().trim(),
                        Integer.valueOf(item_quantity_input.getText().toString().trim()),
                        user_id.trim());
                Intent intent = new Intent(AddItemActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}