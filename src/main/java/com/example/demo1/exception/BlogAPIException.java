package com.example.demo1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
//when we are validating through request parameter so thrown from serviceimpl
public class BlogAPIException extends RuntimeException{
    private HttpStatus status;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus status, String message1) {
        super("not matching id's");
        this.status = status;
        this.message = message1;
    }

    private String message;

}
