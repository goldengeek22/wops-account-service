package com.wops.accountservice.domain;

/**
 * @author Alexandre AMEVOR
 */

public class WopsAccountAlreadyExistsException extends RuntimeException{
    public WopsAccountAlreadyExistsException(String accountNumber){
        super("The Wops account with number "+accountNumber+" already exists.");
    }
}
