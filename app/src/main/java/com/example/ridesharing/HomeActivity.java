package com.example.ridesharing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve user name from SharedPreferences
        String userName = getIntent().getStringExtra("userName");

        // Display the user name in a TextView
        TextView userNameTextView = findViewById(R.id.userNameTextView); // Ensure this ID exists in your layout XML
        userNameTextView.setText("Welcome, " + userName);
    }
}
