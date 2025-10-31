package com.javacalc.exceptions;

/**
 * Exception thrown when division by zero is attempted.
 */
public class DivisionByZeroException extends CalculatorException {
    public DivisionByZeroException() {
        super("ERROR: Division by zero is not allowed");
    }
}
