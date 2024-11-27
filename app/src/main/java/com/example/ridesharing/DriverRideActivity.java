package com.example.ridesharing;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class DriverRideActivity extends AppCompatActivity {

    private MapView driverMapView;
    private Button acceptRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride);

        // Set up OSM configurations
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        // Initialize the MapView
        driverMapView = findViewById(R.id.driverMapView);
        driverMapView.setBuiltInZoomControls(true);
        driverMapView.setMultiTouchControls(true);

        // Retrieve ride details from intent
        String rideDetails = getIntent().getStringExtra("rideDetails");

        // Set dummy pickup and drop-off locations based on ride details (for demonstration purposes)
        GeoPoint pickupPoint = new GeoPoint(27.7172, 85.3240); // Example: Kathmandu
        GeoPoint dropOffPoint = new GeoPoint(27.6727, 85.3188); // Example: Lalitpur

        // Set default view to Kathmandu
        driverMapView.getController().setZoom(12.0);
        driverMapView.getController().setCenter(pickupPoint);

        // Add markers for pickup and drop-off locations
        addMarkerToMap(driverMapView, pickupPoint, "Pickup Location");
        addMarkerToMap(driverMapView, dropOffPoint, "Drop-off Location");

        // Initialize Accept Ride button
        acceptRideButton = findViewById(R.id.acceptRideButton);
        acceptRideButton.setOnClickListener(v -> {
            // Accept the ride and provide feedback
            Toast.makeText(DriverRideActivity.this, "Ride Accepted: " + rideDetails, Toast.LENGTH_LONG).show();
            // You could also send this status to your backend
        });
    }

    private void addMarkerToMap(MapView mapView, GeoPoint point, String title) {
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setTitle(title);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        mapView.invalidate();
    }
}
