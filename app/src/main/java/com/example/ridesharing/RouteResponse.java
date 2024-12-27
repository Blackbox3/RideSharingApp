package com.example.ridesharing;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RouteResponse {
    @SerializedName("routes")
    public List<Route> routes;

    public static class Route {
        @SerializedName("geometry")
        public String geometry;
    }
}
