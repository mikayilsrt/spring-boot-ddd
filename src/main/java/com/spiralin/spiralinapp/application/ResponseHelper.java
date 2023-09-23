package com.spiralin.spiralinapp.application;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseHelper<T> {
    private T data;
    private String message;
    private HttpStatus status;

    public ResponseHelper(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public ResponseEntity<ResponseHelper<T>> sendResponse() {
        return new ResponseEntity<>(this, this.status);
    }

    public static <T> ResponseEntity<ResponseHelper<T>> success(T data, String message) {
        return new ResponseHelper<>(data, message, HttpStatus.OK).sendResponse();
    }

    public static <T> ResponseEntity<ResponseHelper<T>> success(T data) {
        return success(data, "Success");
    }

    public static <T> ResponseEntity<ResponseHelper<T>> error(String message, HttpStatus status) {
        return new ResponseHelper<T>(null, message, status).sendResponse();
    }
}