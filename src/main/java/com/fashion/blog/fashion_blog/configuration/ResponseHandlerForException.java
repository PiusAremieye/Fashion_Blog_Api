package com.fashion.blog.fashion_blog.configuration;

import com.fashion.blog.fashion_blog.custom_exception.MyCustomExceptionHandler;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseHandlerForException extends ResponseEntityExceptionHandler {

    // return the response of the exception handler
    private ResponseEntity<Object> createResponseEntity(ApiExceptionHandler<Object> apiExceptionHandler){
        return new ResponseEntity<>(apiExceptionHandler, apiExceptionHandler.getStatus());
    }

    // override the method argument not valid exception and handle the exception
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request){
        ApiExceptionHandler<Object> arh = new ApiExceptionHandler<>(HttpStatus.METHOD_NOT_ALLOWED);
        arh.setError("Invalid method request");
        arh.setDebugMessage("Change method name to the correct one");
        return createResponseEntity(arh);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception, HttpHeaders headers, HttpStatus status, WebRequest request){
        ApiExceptionHandler<Object> arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setError("Type Mismatch");
        arh.setDebugMessage("The endpoint has a type mismatch, change to the correct type");
        return createResponseEntity(arh);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiExceptionHandler<Object> arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.addErrors(exception.getBindingResult().getFieldErrors());
        arh.setError("Error in one of the fields");
        arh.setDebugMessage("Check the fields for any error");
        return createResponseEntity(arh);
    }

    // handle custom exception
    @ExceptionHandler(MyCustomExceptionHandler.class)
    public ResponseEntity<Object> handleMyCustomException(MyCustomExceptionHandler myCustomExceptionHandler){
        ApiExceptionHandler<Object> arh = new ApiExceptionHandler<>(myCustomExceptionHandler.getStatus());
        arh.setMessage(myCustomExceptionHandler.getMessage());
        return createResponseEntity(arh);
    }
}
