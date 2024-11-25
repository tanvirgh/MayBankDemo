package com.accord.exception;


/**
 * User : Tanvir Ahmed
 * Date: 11/20/2024.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
