package com.example.ridesharing.api;

import com.example.ridesharing.RouteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OsrmApi {
    @GET("route/v1/driving/{startLng},{startLat};{endLng},{endLat}?overview=full&steps=true")
    Call<RouteResponse> getRoute(
            @Path("startLng") double startLng,
            @Path("startLat") double startLat,
            @Path("endLng") double endLng,
            @Path("endLat") double endLat
    );
}
