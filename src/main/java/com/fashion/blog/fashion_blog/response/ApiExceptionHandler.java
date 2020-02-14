package com.fashion.blog.fashion_blog.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiExceptionHandler<T> {
    private HttpStatus status;
    private String message;
    private String error;
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss dd/MM/yyyy")
    private LocalDateTime time;
    private String debugMessage;
    private List<ApiError> subErrors;

    public ApiExceptionHandler() {
        time = LocalDateTime.now();
    }

    public ApiExceptionHandler(HttpStatus status){
        this();
        this.status = status;
    }

    public ApiExceptionHandler(HttpStatus status, String error, Throwable exception){
        this();
        this.status = status;
        this.error = error;
        this.debugMessage = exception.getLocalizedMessage();
    }

    public ApiExceptionHandler(HttpStatus status, String message, T data) {
        this();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private void addSubErrors(ApiError subError){
        if (subErrors == null){
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    public void addError(String field, String message){
        addSubErrors(new ApiErrorChecker(message, field));
    }

    public void addError( String object, String field, Object rejectValue, String message){
        addSubErrors(new ApiErrorChecker(object, field, rejectValue, message));
    }

    private void addError(FieldError fieldError){
        addSubErrors(new ApiErrorChecker(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
    }

    public void addErrors(List<FieldError> fieldErrors){
        fieldErrors.forEach(this::addError);
    }

    private void addError(ObjectError objectError){
        addSubErrors(new ApiErrorChecker(objectError.getObjectName(), objectError.getDefaultMessage()));
    }

    public void addError(List<ObjectError> globalErrors){
        globalErrors.forEach(this::addError);
    }

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<ApiError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiError> subErrors) {
        this.subErrors = subErrors;
    }
}
