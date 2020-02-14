package com.fashion.blog.fashion_blog.custom_exception;


import org.springframework.http.HttpStatus;

public class MyCustomExceptionHandler extends RuntimeException {
    private String message;
    private HttpStatus status;

    public MyCustomExceptionHandler(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
