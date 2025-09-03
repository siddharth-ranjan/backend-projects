package com.hackathon.realtimechatapplication.shared.security.dto;

public class AuthResponse {

    private boolean success;
    private String data;

    public AuthResponse() {
    }

    public AuthResponse(boolean success, String data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
