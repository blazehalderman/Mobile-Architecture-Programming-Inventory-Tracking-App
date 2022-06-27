package com.example.cs360blazehaldermanprojecttwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_request);

        TextView username, password, password2;
        Button cancel, reset;

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        password2 = (TextView) findViewById(R.id.password2);
        cancel = (Button) findViewById(R.id.cancelResetButton);
        reset = (Button) findViewById(R.id.passwordResetButton);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Reset Password (In Progress)");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
