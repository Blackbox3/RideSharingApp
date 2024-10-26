package com.example.ridesharing;

public class RegistrationResponse {
    private String message;   // Success message or error message
    private boolean success;   // Indicating whether the registration was successful

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
