package com.javafinal.Model;

public class CustomerNotFoundException extends Throwable{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
