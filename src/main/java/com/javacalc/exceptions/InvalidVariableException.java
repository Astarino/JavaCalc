package com.javacalc.exceptions;

/**
 * Exception thrown when an invalid variable name is used.
 */
public class InvalidVariableException extends CalculatorException {
    public InvalidVariableException(String message) {
        super(message);
    }
}
