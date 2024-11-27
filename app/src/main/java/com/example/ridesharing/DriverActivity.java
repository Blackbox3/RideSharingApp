package com.example.ridesharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {

    private ListView rideRequestsListView;
    private ArrayList<String> rideRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        // Initialize the ListView and ride requests
        rideRequestsListView = findViewById(R.id.rideRequestsListView);
        rideRequests = new ArrayList<>();
        loadDummyRideRequests();  // Load some dummy ride requests for testing

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, rideRequests);
        rideRequestsListView.setAdapter(adapter);

        // Set click listener for ListView items
        rideRequestsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRide = rideRequests.get(position);
            // Open the ride details page (DriverRideActivity)
            Intent intent = new Intent(DriverActivity.this, DriverRideActivity.class);
            intent.putExtra("rideDetails", selectedRide);
            startActivity(intent);
        });
    }

    private void loadDummyRideRequests() {
        // Dummy ride requests
        rideRequests.add("Ride Request 1: Pickup at Thamel, Drop-off at Durbar Marg");
        rideRequests.add("Ride Request 2: Pickup at Patan, Drop-off at Boudhanath");
        rideRequests.add("Ride Request 3: Pickup at Bhaktapur, Drop-off at Swayambhunath");
    }
}
