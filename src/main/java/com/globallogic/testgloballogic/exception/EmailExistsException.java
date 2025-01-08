package com.globallogic.testgloballogic.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(final String email) {
        super(String.format("The email %s is already registered", email));
    }
}
