package com.api.skillShare.exception;

import lombok.Getter;

@Getter
public class ErrorDetails {
    private String message;
    private int statusCode;
    private long timestamp;

    public ErrorDetails(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis();
    }
}
