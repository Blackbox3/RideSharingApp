package com.example.ridesharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RiderActivity extends AppCompatActivity {
    private Button LogOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        // Your rider-specific logic here
        LogOutButton = findViewById(R.id.buttonLogOut);  // Ensure this ID matches the one in your XML

        // Initialize "Request a Ride" button
        Button requestRideButton = findViewById(R.id.buttonRequestRide);
        Button LogOutButton =findViewById(R.id.buttonLogOut);
        // Set click listener to open the map activity
        requestRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiderActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // Set up the LogOut button click listener
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Optionally, clear session data (SharedPreferences, user data, etc.)
                // Example (if you have session data stored in SharedPreferences):
                // SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                // SharedPreferences.Editor editor = prefs.edit();
                // editor.clear();
                // editor.apply();

                // Finish the current activity (RiderActivity) to clear it from the back stack
                finish();  // Close the RiderActivity

                // Start LoginActivity (where the login form is displayed)
                Intent intent = new Intent(RiderActivity.this, LoginActivity.class);
                startActivity(intent);  // Open LoginActivity
            }
        });
    }
}
