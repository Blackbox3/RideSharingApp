package com.example.ridesharing;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private AutoCompleteTextView pickupLocationEditText, dropOffLocationEditText;
    private Button requestRideButton;
    private Marker pickupMarker, dropOffMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up OSM configurations
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_map);

        // Initialize the MapView
        mapView = findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Set default view to Kathmandu
        GeoPoint kathmanduCenter = new GeoPoint(27.7172, 85.3240); // Coordinates of Kathmandu
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(kathmanduCenter);

        // Initialize form elements
        pickupLocationEditText = findViewById(R.id.pickupLocation);
        dropOffLocationEditText = findViewById(R.id.dropOffLocation);
        requestRideButton = findViewById(R.id.requestRideButton);

        // Add text watchers to trigger autocomplete search
        pickupLocationEditText.addTextChangedListener(new LocationTextWatcher(pickupLocationEditText, true));
        dropOffLocationEditText.addTextChangedListener(new LocationTextWatcher(dropOffLocationEditText, false));

        // Set click listener for Request Ride button
        requestRideButton.setOnClickListener(v -> {
            if (pickupMarker == null || dropOffMarker == null) {
                Toast.makeText(MapActivity.this, "Please select both pickup and drop-off locations.", Toast.LENGTH_SHORT).show();
            } else {
                requestRide(pickupLocationEditText.getText().toString(), dropOffLocationEditText.getText().toString());
            }
        });
    }

    private void requestRide(String pickupLocation, String dropOffLocation) {
        // Here, you could send the pickup and drop-off location data to your backend
        // and handle ride requests accordingly.
        Toast.makeText(MapActivity.this, "Ride Requested from " + pickupLocation + " to " + dropOffLocation, Toast.LENGTH_LONG).show();
    }

    // TextWatcher to trigger Nominatim API for address suggestions
    private class LocationTextWatcher implements TextWatcher {
        private AutoCompleteTextView editText;
        private boolean isPickup;

        public LocationTextWatcher(AutoCompleteTextView editText, boolean isPickup) {
            this.editText = editText;
            this.isPickup = isPickup;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            String query = s.toString();
            if (query.length() > 2) { // Start searching if input length is greater than 2
                new NominatimSearchTask(editText, isPickup).execute(query);
            }
        }
    }

    // AsyncTask to perform Nominatim search for address suggestions
    private class NominatimSearchTask extends AsyncTask<String, Void, List<String>> {
        private AutoCompleteTextView autoCompleteTextView;
        private boolean isPickup;

        public NominatimSearchTask(AutoCompleteTextView autoCompleteTextView, boolean isPickup) {
            this.autoCompleteTextView = autoCompleteTextView;
            this.isPickup = isPickup;
        }

        @Override
        protected List<String> doInBackground(String... params) {
            String query = params[0];
            String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + query + "&addressdetails=1&limit=5&countrycodes=np";

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                List<String> suggestions = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String displayName = jsonObject.getString("display_name");
                    suggestions.add(displayName);
                }

                return suggestions;

            } catch (Exception e) {
                Log.e("NominatimSearchTask", "Error in autocomplete search", e);
                return null;
            }
        }
``
        @Override
        protected void onPostExecute(List<String> suggestions) {
            if (suggestions != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MapActivity.this,
                        android.R.layout.simple_dropdown_item_1line, suggestions);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.showDropDown();

                autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                    String selectedAddress = suggestions.get(position);
                    new NominatimGeocodeTask(selectedAddress, isPickup).execute();
                });
            } else {
                Toast.makeText(MapActivity.this, "Unable to retrieve suggestions. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // AsyncTask to get coordinates for the selected address
    private class NominatimGeocodeTask extends AsyncTask<Void, Void, GeoPoint> {
        private String address;
        private boolean isPickup;

        public NominatimGeocodeTask(String address, boolean isPickup) {
            this.address = address;
            this.isPickup = isPickup;
        }

        @Override
        protected GeoPoint doInBackground(Void... voids) {
            String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + address + "&limit=1&countrycodes=np";

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");
                    return new GeoPoint(lat, lon);
                }

            } catch (Exception e) {
                Log.e("NominatimGeocodeTask", "Error in geocoding", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(GeoPoint point) {
            if (point != null) {
                if (isPickup) {
                    if (pickupMarker != null) {
                        mapView.getOverlays().remove(pickupMarker);
                    }
                    pickupMarker = new Marker(mapView);
                    pickupMarker.setPosition(point);
                    pickupMarker.setTitle("Pickup Location");
                    pickupMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    mapView.getOverlays().add(pickupMarker);
                    mapView.getController().animateTo(point);
                } else {
                    if (dropOffMarker != null) {
                        mapView.getOverlays().remove(dropOffMarker);
                    }
                    dropOffMarker = new Marker(mapView);
                    dropOffMarker.setPosition(point);
                    dropOffMarker.setTitle("Drop-off Location");
                    dropOffMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    mapView.getOverlays().add(dropOffMarker);
                    mapView.getController().animateTo(point);
                }
                mapView.invalidate();
            } else {
                Toast.makeText(MapActivity.this, "Unable to retrieve coordinates. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
