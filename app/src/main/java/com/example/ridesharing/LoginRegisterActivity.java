package com.example.ridesharing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginRegisterActivity extends AppCompatActivity {
   private Button LogOutButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        findViewById(R.id.buttonLogin).setOnClickListener(v -> {
            Intent loginIntent = new Intent(LoginRegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        findViewById(R.id.buttonRegister).setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

    }
}