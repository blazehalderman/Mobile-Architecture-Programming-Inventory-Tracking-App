package com.example.cs360blazehaldermanprojecttwo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateItemActivity extends AppCompatActivity {

    EditText name_input, quantity_input;
    Button update_item, delete_item;

    String id, name, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        name_input = findViewById(R.id.UpdateItemName);
        quantity_input = findViewById(R.id.UpdateItemQuantity);
        update_item = findViewById(R.id.update_item_button);
        delete_item = findViewById(R.id.delete_item_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = LoginActivity.sessionManagement;
                String user_id = sessionManagement.getSession();

                InventoryDBHelper DB = new InventoryDBHelper(UpdateItemActivity.this);
                name = name_input.getText().toString().trim();
                quantity = quantity_input.getText().toString().trim();
                DB.updateData(id, name, quantity, user_id);
                Intent intent = new Intent(UpdateItemActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("item_id") && getIntent().hasExtra("name") && getIntent().hasExtra("quantity")) {
            id = getIntent().getStringExtra("item_id");
            name = getIntent().getStringExtra("name");
            quantity = getIntent().getStringExtra("quantity");

            name_input.setText(name);
            quantity_input.setText(quantity);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Are you sure you want to delete " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InventoryDBHelper DB = new InventoryDBHelper(UpdateItemActivity.this);
                DB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}