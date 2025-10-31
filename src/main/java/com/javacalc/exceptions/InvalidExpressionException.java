package com.javacalc.exceptions;

/**
 * Exception thrown when an expression is malformed or invalid.
 */
public class InvalidExpressionException extends CalculatorException {
    public InvalidExpressionException(String message) {
        super(message);
    }
}
