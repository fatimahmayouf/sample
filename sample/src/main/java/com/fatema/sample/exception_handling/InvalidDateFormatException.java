package com.fatema.sample.exception_handling;

public class InvalidDateFormatException  extends RuntimeException {

    public InvalidDateFormatException (String message) {
        super(message);
    }
}
