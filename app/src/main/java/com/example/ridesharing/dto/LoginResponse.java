package com.example.ridesharing.dto;

public class LoginResponse {

    private String username;
    private String firstName;
    private String lastName;
    private String token;
    private String userType;
    private String name;
    private Long userId;
    private String email; // Changed from Email to email

    // Default constructor
    public LoginResponse() {}

    // Constructor with parameters (optional)
    public LoginResponse(String username, String firstName, String lastName, String token, String userType, String name, Long userId, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.userType = userType;
        this.name = name;
        this.userId = userId;
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() {
        return email; // Changed from Email to email
    }

    public void setEmail(String email) {
        this.email = email; // Changed from Email to email
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", token='" + token + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }
}
