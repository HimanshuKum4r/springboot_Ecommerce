package com.ecommerce.sb.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends APIException {
     private  Long fieldId;

    public ResourceNotFoundException (String message,Long fieldId){
        super(message, HttpStatus.NOT_FOUND);
        this.fieldId=fieldId;


    }
    public ResourceNotFoundException (String message){
        super(message, HttpStatus.NOT_FOUND,"USER_NOT_FOUND");


    }



}
