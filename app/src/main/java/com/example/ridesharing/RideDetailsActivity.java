package com.example.ridesharing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridesharing.api.ApiClient;
import com.example.ridesharing.api.LoginApi;
import com.example.ridesharing.dto.BaseResponse;
import com.example.ridesharing.dto.RideRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideDetailsActivity extends AppCompatActivity {
    private LoginApi rideApi;

    private EditText startLocationEditText, endLocationEditText;
    private TextView fareTextView, distanceTextView;
    private Button submitRideDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        startLocationEditText = findViewById(R.id.pickupLocation);
        endLocationEditText = findViewById(R.id.dropOffLocation);
        fareTextView = findViewById(R.id.fareTextView);
        distanceTextView = findViewById(R.id.distanceTextView);
        submitRideDetailsButton = findViewById(R.id.requestRideButton);

        // Initialize Retrofit for RideApi
        rideApi = ApiClient.getClient().create(LoginApi.class);
        // Retrieve data from previous activity (MapActivity)
        Intent intent = getIntent();
        double pickupLatitude = intent.getDoubleExtra("pickupLatitude", 0.0);
        double pickupLongitude = intent.getDoubleExtra("pickupLongitude", 0.0);
        double dropOffLatitude = intent.getDoubleExtra("dropOffLatitude", 0.0);
        double dropOffLongitude = intent.getDoubleExtra("dropOffLongitude", 0.0);
        double distance = intent.getDoubleExtra("distance", 0.0);
        double fare = intent.getDoubleExtra("fare", 0.0);

        submitRideDetailsButton.setOnClickListener(v -> {
            String startLocation = startLocationEditText.getText().toString().trim();
            String endLocation = endLocationEditText.getText().toString().trim();
            Double fareValue = Double.parseDouble(fareTextView.getText().toString().trim());
            String distanceValue = distanceTextView.getText().toString().trim();

            // Validate input
            if (validateInput(startLocation, endLocation, fareValue, distanceValue)) {
                // Get the rider's id and name from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                Long riderId = sharedPreferences.getLong("userId", -1);
                String riderName = sharedPreferences.getString("userName", "Guest");

                // Create a RideRequestDTO object
                RideRequestDTO rideDetails = new RideRequestDTO(
                        riderId,
                        riderName,
                        fareValue,
                        startLocation,
                        endLocation,
                        String.valueOf(pickupLatitude),  // Replace with actual coordinates if needed
                        String.valueOf(pickupLongitude), // Replace with actual coordinates if needed
                        String.valueOf(dropOffLatitude),    // Replace with actual coordinates if needed
                        String.valueOf(dropOffLongitude),   // Replace with actual coordinates if needed
                        distanceValue
                );

                // Submit ride details to the server
                submitRideDetails(rideDetails);
            }
        });
    }

    private boolean validateInput(String startLocation, String endLocation, Double fare, String distance) {
        if (startLocation.isEmpty() || endLocation.isEmpty() || fare <= 0 || distance.isEmpty()) {
            showToast("Please fill out all required fields");
            return false;
        }
        return true;
    }

    private void submitRideDetails(RideRequestDTO rideRequestDTO) {
        Call<BaseResponse<RideRequestDTO>> call = rideApi.create(rideRequestDTO);

        call.enqueue(new Callback<BaseResponse<RideRequestDTO>>() {
            @Override
            public void onResponse(Call<BaseResponse<RideRequestDTO>> call, Response<BaseResponse<RideRequestDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showToast("Ride details submitted successfully");
                    navigateToNextActivity();
                } else {
                    showToast("Failed to submit ride details: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RideRequestDTO>> call, Throwable t) {
                showToast("Network error: " + t.getMessage());
            }
        });
    }

    private void navigateToNextActivity() {
        Intent intent = new Intent(RideDetailsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(RideDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
