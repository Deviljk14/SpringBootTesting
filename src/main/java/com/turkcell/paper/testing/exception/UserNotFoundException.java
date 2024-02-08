package com.turkcell.paper.testing.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super(String.format("Email does not exist. Please create an account."));
    }
}
