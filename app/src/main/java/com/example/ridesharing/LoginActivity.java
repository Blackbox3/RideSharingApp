package com.example.ridesharing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ridesharing.api.ApiClient;
import com.example.ridesharing.api.LoginApi;
import com.example.ridesharing.dto.BaseResponse;
import com.example.ridesharing.dto.LoginResponse;
import com.example.ridesharing.dto.RideRequestDTO;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 101;
    private LoginApi loginApi;
    private LoginApi rideApi;

    private EditText editTextEmail, editTextPassword;
    private CheckBox checkboxShowPassword;
    private Button buttonLoginSubmit;
    private Button requestRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        CheckBox checkboxShowPassword = findViewById(R.id.checkboxShowPassword);
        Button loginButton = findViewById(R.id.buttonLoginSubmit);
        checkboxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show the password
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Hide the password
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            // Move the cursor to the end of the text after transformation changes
            passwordEditText.setSelection(passwordEditText.length());
        });


        loginApi = ApiClient.getClient().create(LoginApi.class);

        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                if (validateInput(email, password)) {
                    performLogin(email, password);
                }
            }
        });


    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please fill out all required fields");
            return false;
        } else if (!isValidEmail(email)) {
//            showToast("Please enter a valid email address");
//            return false;
        }
        return true;
    }

    private void performLogin(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(email);
        loginRequest.setPassword(password);
        Call<BaseResponse<LoginResponse>> call = loginApi.login(loginRequest);

        call.enqueue(new Callback<BaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginResponse>> call, Response<BaseResponse<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleLoginResponse(response.body().getObj());
                } else {
                    showToast("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginResponse>> call, Throwable t) {
                showToast("Network error: " + t.getMessage());
            }
        });
    }



    private void handleLoginResponse(LoginResponse loginResponse) {
        if (loginResponse != null) {
            showToast("Login Successful");
            saveUserSession(loginResponse);
            navigateToNextActivity(loginResponse.getUserType());
        } else {
            showToast("Login failed: Invalid credentials");
        }
    }

    private void saveUserSession(LoginResponse loginResponse) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", loginResponse.getUsername());
        editor.putString("userToken", loginResponse.getToken());
        editor.putString("userType", loginResponse.getUserType());
        editor.putString("userId",loginResponse.getUserId().toString());
        editor.putString("name", loginResponse.getName());

        editor.apply();

        Log.d("LoginActivity", "User type saved: " + loginResponse.getUserType());
    }

    private void navigateToNextActivity(String userType) {
        Intent intent;
        System.out.println("User type: " + userType);

        if (userType.equalsIgnoreCase("Driver")) {
            intent = new Intent(LoginActivity.this, DriverActivity.class);
        } else if (userType.equalsIgnoreCase("Rider")) {
            intent = new Intent(LoginActivity.this, RiderActivity.class);
        } else {
            // Optionally handle other cases or default behavior
            System.out.println("Unknown user type: " + userType);
            return;
        }

        startActivity(intent);


    finish();
}



    //        if ("driver".equals(userType)) {
//            intent = new Intent(LoginActivity.this, DriverActivity.class);
//        } else {
//            intent = new Intent(LoginActivity.this, RiderActivity.class);
//        }
//        startActivity(intent);
//        finish(); // Close the LoginActivity
//    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }
}
