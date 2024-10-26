package com.example.ridesharing.dto;

public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T obj; // This should hold the actual response data

    // Getters and setters for success, message, and obj
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
