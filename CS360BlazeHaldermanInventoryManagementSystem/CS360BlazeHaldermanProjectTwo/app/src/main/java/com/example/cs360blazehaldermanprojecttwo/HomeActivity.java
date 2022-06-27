package com.example.cs360blazehaldermanprojecttwo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    InventoryDBHelper myDB;
    ArrayList<String> item_id, item_name, item_date, item_quantity;
    CustomAdapter customAdapter;
    DBHelper userDB;
    public static String user_id;
    public static int notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_information);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.floatingActionButton2);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Inventory Management System");
        }

        SessionManagement sessionManagement = LoginActivity.sessionManagement;
        user_id = sessionManagement.getSession();
        userDB = new DBHelper(this);
        String username = userDB.getUsername(user_id);
        //Toast.makeText(this, "THIS IS THE HOME " + username, Toast.LENGTH_SHORT).show();

        if (notification == 0) {
            alertSMSNotification();
        }

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        myDB = new InventoryDBHelper(HomeActivity.this);
        item_id = new ArrayList<>();
        item_name = new ArrayList<>();
        item_date = new ArrayList<>();
        item_quantity = new ArrayList<>();

        storedDataInArrays();

        customAdapter = new CustomAdapter(HomeActivity.this, this, item_id, item_date, item_name, item_quantity);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }


    void storedDataInArrays() {
        Cursor cursor = myDB.readAllData(user_id);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                item_id.add(cursor.getString(0));
                item_name.add(cursor.getString(1));
                item_date.add(cursor.getString(2));
                item_quantity.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SessionManagement sessionManagement = LoginActivity.sessionManagement;
            sessionManagement.removeSession();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void alertSMSNotification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.text_message_prompt).setTitle(R.string.text_message_permission_title);

        builder.setMessage("Do you want to allow SMS Communication for the application?")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        notification = 1;
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        notification = 0;
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();

        alert.setTitle("SMS Alert: Allow Comms");
        alert.show();
    }

}
