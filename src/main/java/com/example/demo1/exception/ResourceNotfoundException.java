package com.example.demo1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotfoundException extends RuntimeException{
    private String ResourceName;
    private String filedname;
    private Long fieldvalue;

    public ResourceNotfoundException( String resourceName, String filedname, Long fieldvalue) {
        super(String.format("not found with this id"));
        ResourceName = resourceName;
        this.filedname = filedname;
        this.fieldvalue = fieldvalue;
    }

    public String getResourceName() {
        return ResourceName;
    }

    public String getFiledname() {
        return filedname;
    }


    public Long getFieldvalue() {
        return fieldvalue;
    }





}
