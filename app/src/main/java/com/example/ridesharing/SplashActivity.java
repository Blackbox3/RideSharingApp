package com.example.ridesharing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  // Ensure this layout has your logo and "Get Started" button

        // Delay for 2 seconds before navigating to the LoginRegisterActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this,LoginRegisterActivity.class);
            startActivity(intent);
            finish(); // Finish SplashActivity so user cannot go back to it
        }, 2000); // 2000 milliseconds = 2-second delay
    }
}
