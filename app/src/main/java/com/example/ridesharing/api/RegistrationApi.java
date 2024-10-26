package com.example.ridesharing.api;

import com.example.ridesharing.RegistrationRequest;
import com.example.ridesharing.dto.BaseResponse;
import com.example.ridesharing.RegistrationResponse; // Make sure to import this class

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationApi {
    @POST("register/user")
    Call<BaseResponse<RegistrationResponse>> registerUser(@Body RegistrationRequest registrationRequest);
}
