package com.example.ridesharing;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.ridesharing.dto.BaseResponse;
import com.example.ridesharing.RegistrationResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hbb20.CountryCodePicker;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.NumberParseException;

import com.example.ridesharing.api.ApiClient;
import com.example.ridesharing.api.RegistrationApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private CountryCodePicker countryCodePicker;
    private RegistrationApi registrationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing UI components
        EditText nameEditText = findViewById(R.id.editTextFullName);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText phoneEditText = findViewById(R.id.editTextPhone);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Spinner roleSpinner = findViewById(R.id.spinnerRole);
        LinearLayout driverDetailsLayout = findViewById(R.id.layoutDriverDetails);
        EditText licensePlateEditText = findViewById(R.id.editTextLicensePlate);
        EditText bikeNumberEditText = findViewById(R.id.editTextBikeNumber);
        Button registerButton = findViewById(R.id.buttonRegisterSubmit);
        countryCodePicker = findViewById(R.id.countryCodePicker);

        // Initialize Retrofit
        registrationApi = ApiClient.getClient().create(RegistrationApi.class);

        // Attach the CountryCodePicker to the phone number EditText
        countryCodePicker.registerCarrierNumberEditText(phoneEditText);

        // Set up spinner for selecting Rider or Driver
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Show or hide driver details based on role selection
        roleSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = parent.getItemAtPosition(position).toString();
                driverDetailsLayout.setVisibility(selectedRole.equals("Driver") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                driverDetailsLayout.setVisibility(View.GONE);
            }
        });
        Toast.makeText(RegisterActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();

    }
    private void registerUser(EditText nameEditText, EditText emailEditText, EditText phoneEditText,
                              EditText passwordEditText, Spinner roleSpinner,
                              EditText licensePlateEditText, EditText bikeNumberEditText) {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phoneNumber = countryCodePicker.getFullNumberWithPlus();
        String password = passwordEditText.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();
        String licensePlate = licensePlateEditText.getText().toString().trim();
        String bikeNumber = bikeNumberEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() ||
                (role.equals("Driver") && (licensePlate.isEmpty() || bikeNumber.isEmpty()))) {
            Toast.makeText(RegisterActivity.this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else {
            // Create RegistrationRequest object
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setUsername(name);
            registrationRequest.setEmailAddress(email);
            registrationRequest.setMobileNumber(phoneNumber);
            registrationRequest.setPassword(password);
            registrationRequest.setUserType(role);
            registrationRequest.setLicenseNumber(licensePlate);
            registrationRequest.setBikeName(bikeNumber);
            registrationRequest.setName(name);

            // Call the registration API

            Call<BaseResponse<RegistrationResponse>> call = registrationApi.registerUser(registrationRequest);
            call.enqueue(new Callback<BaseResponse<RegistrationResponse>>() {
                @Override
                public void onResponse(Call<BaseResponse<RegistrationResponse>> call, Response<BaseResponse<RegistrationResponse>> response) {
                    System.out.println("response"+response.message());
                    BaseResponse baseResponse = (BaseResponse) response.body();

                    if (response.body() != null && response.body().isSuccess()) {
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show());
                        // TODO: Navigate to the main page
                        Intent loginIntent = new Intent(RegisterActivity.this, RegisterActivity.class);
                        startActivity(loginIntent);
                    } else if (response.body() != null) {
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Registration failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Unexpected error occurred.", Toast.LENGTH_SHORT).show());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<RegistrationResponse>> call, Throwable t) {
                    runOnUiThread(()->Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = phoneUtil.parse(phoneNumber, countryCodePicker.getSelectedCountryCode());
            return phoneUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
