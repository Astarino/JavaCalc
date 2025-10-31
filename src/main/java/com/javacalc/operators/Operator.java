package com.javacalc.operators;

/**
 * Represents a mathematical operator with its symbol, precedence, and operation.
 */
public class Operator {
    private final char symbol;
    private final int precedence;
    private final BinaryOperation operation;

    /**
     * Functional interface for binary operations.
     */
    @FunctionalInterface
    public interface BinaryOperation {
        double apply(double a, double b);
    }

    public Operator(char symbol, int precedence, BinaryOperation operation) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.operation = operation;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public double apply(double a, double b) {
        return operation.apply(a, b);
    }
}
