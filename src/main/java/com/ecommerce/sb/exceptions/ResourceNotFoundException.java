package com.ecommerce.sb.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends APIException {

    public ResourceNotFoundException (String message){
        super(message, HttpStatus.NOT_FOUND,"USER_NOT_FOUND");
    }


}
