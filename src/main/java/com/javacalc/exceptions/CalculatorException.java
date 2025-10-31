package com.javacalc.exceptions;

/**
 * Base exception class for all calculator-related errors.
 */
public class CalculatorException extends RuntimeException {
    public CalculatorException(String message) {
        super(message);
    }

    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
