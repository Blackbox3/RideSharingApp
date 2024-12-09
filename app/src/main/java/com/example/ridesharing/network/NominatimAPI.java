package com.example.ridesharing.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NominatimAPI {

    public static void geocode(String address, GeocodeListener listener) {
        new GeocodeTask(address, listener).execute();
    }

    private static class GeocodeTask extends AsyncTask<Void, Void, GeoPoint> {
        private String address;
        private GeocodeListener listener;

        public GeocodeTask(String address, GeocodeListener listener) {
            this.address = address;
            this.listener = listener;
        }

        @Override
        protected GeoPoint doInBackground(Void... voids) {
            // Updated URL with bounding box around Kathmandu and countrycodes for Nepal
            String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + address +
                    "&limit=1&countrycodes=np&bounded=1&viewbox=85.299,27.616,85.434,27.828";

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

                // Parse the response
                JSONArray jsonArray = new JSONArray(response.toString());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");
                    return new GeoPoint(lat, lon);
                }
            } catch (Exception e) {
                Log.e("GeocodeTask", "Error in geocoding", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(GeoPoint geoPoint) {
            if (geoPoint != null) {
                listener.onGeocodeSuccess(geoPoint);
            } else {
                listener.onGeocodeError("Failed to geocode address");
            }
        }
    }

    public interface GeocodeListener {
        void onGeocodeSuccess(GeoPoint geoPoint);
        void onGeocodeError(String errorMessage);
    }
}
