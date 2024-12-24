package com.example.demo1.exception;

import com.example.demo1.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<ErrorDetails> handleResourcennotfoundexcepion(ResourceNotfoundException exception, WebRequest web) {
        ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleblogapiexcepion(BlogAPIException exception, WebRequest web) {
        ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    //sari exceptions ko handle karra
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleallexceotion(Exception exception, WebRequest web) {
        ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //for cutomising validation errors @VALID @NOTNULL
// extended ResponseEntityExceptionHandler for below method onlyyyy
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String meessageerror = error.getDefaultMessage();
            errors.put(fieldName, meessageerror);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
//this made in spring security that api's which are accessed by admin and not by user role
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleResourcennotfoundexcepion(AccessDeniedException exception, WebRequest web) {
        ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

    }
}
