package com.stockmarket.company.entity;

import org.springframework.http.HttpStatus;

public class ExceptionJSONInfo {
    private String message;
    private HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}