//package com.example.ridesharing;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class WelcomeActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Check if user is already logged in
//        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
//        String userType = sharedPreferences.getString("userType", null);
//
//        if (userType != null) {
//            // User is logged in, navigate to appropriate activity
//            Intent intent;
//            if ("driver".equals(userType)) {
//                intent = new Intent(WelcomeActivity.this, DriverActivity.class);
//            } else {
//                intent = new Intent(WelcomeActivity.this, RiderActivity.class);
//            }
//            startActivity(intent);
//            finish(); // Close WelcomeActivity
//            return; // Exit onCreate
//        }
//
//        setContentView(R.layout.activity_login_register);
//
//        findViewById(R.id.buttonLogin).setOnClickListener(v -> {
//            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
//            startActivity(loginIntent);
//        });
//
//        findViewById(R.id.buttonRegister).setOnClickListener(v -> {
//            Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
//            startActivity(registerIntent);
//        });
//    }
//}
