package com.wops.accountservice.domain;

/**
 * @author Alexandre AMEVOR
 */
public class WopsAccountNotFoundException extends RuntimeException{
    public WopsAccountNotFoundException(String accountNumber){
        super("The Wops account with number "+accountNumber+" was not found.");
    }
}
