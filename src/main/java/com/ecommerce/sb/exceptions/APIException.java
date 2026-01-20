package com.ecommerce.sb.exceptions;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException{
     private HttpStatus status;
     private String errorcode;

    public APIException() {
    }

    public APIException (String message, HttpStatus status, String errorcode){
         super(message);
         this.errorcode =errorcode;
         this.status = status;
     }
    public APIException (String message, HttpStatus status){
        super(message);
        this.errorcode =null;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorcode() {
        return errorcode;
    }


}
