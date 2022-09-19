package com.turkcell.paper.testing.exception;

public class InvalidCustomerRequestException extends RuntimeException{
    public InvalidCustomerRequestException(String message) {
        super(message);
    }
}
