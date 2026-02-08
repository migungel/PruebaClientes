package com.test.neoris.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private String message;
    private T data;

    public ApiResponse(String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(T data) {
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
