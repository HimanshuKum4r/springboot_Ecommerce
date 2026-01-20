package com.ecommerce.sb.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends APIException {
    public UserAlreadyExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST,"USER_ALREADY_EXISTS");
    }
}
