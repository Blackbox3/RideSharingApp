package com.example.ridesharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Button buttonGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize the "Get Started" button
        buttonGetStarted = findViewById(R.id.buttonGetStarted);

        // Set onClickListener for the "Get Started" button
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the LoginRegisterActivity when "Get Started" is clicked
                Intent intent = new Intent(SplashActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
                finish(); // Finish SplashActivity so that the user cannot go back to it
            }
        });
    }
}
