package com.turkcell.paper.testing.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super(String.format("Customer with ID: %d not found", id));
    }
}
