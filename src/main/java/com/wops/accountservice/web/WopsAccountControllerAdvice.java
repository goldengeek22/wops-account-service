package com.wops.accountservice.web;

import com.wops.accountservice.domain.WopsAccountAlreadyExistsException;
import com.wops.accountservice.domain.WopsAccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandre AMEVOR
 */

@RestControllerAdvice
public class WopsAccountControllerAdvice {

    @ExceptionHandler(WopsAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleWopsAccountNotFound(WopsAccountNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(WopsAccountAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String handleWopsAccountAlreadyExists(WopsAccountAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBeanValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String,String>();
        ex.getBindingResult().getAllErrors().forEach((error)->errors.put(((FieldError)error).getField(),error.getDefaultMessage()));
        return errors;
    }
}
