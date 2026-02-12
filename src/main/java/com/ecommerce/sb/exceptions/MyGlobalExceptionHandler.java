package com.ecommerce.sb.exceptions;


import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodargumentnotvalidexception(MethodArgumentNotValidException e){
     Map<String,String> errors = new HashMap<>();
     e.getBindingResult().getFieldErrors().forEach(ex -> errors.put(ex.getField(), ex.getDefaultMessage()));
//     Map<String,Object> response =  new HashMap<>();
//
//     response.put("success", false);
//     response.put("message","validation failed");
//     response.put("errors",errors);

     return ResponseEntity.badRequest().body(errors);
 }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<Map<String,String>> ResourceNotFoundException(APIException ex){
           Map<String,String> ressponse = new HashMap<>();

           ressponse.put("success","false");
           ressponse.put("message", ex.getMessage());
           ressponse.put("errorcode", ex.getErrorcode());

        return ResponseEntity.status(ex.getStatus()).body(ressponse);

    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> IOException(IOException ex){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("File operation failed"+ ex.getMessage());
    }



}
