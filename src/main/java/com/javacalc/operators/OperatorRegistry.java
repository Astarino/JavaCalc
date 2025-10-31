package com.javacalc.operators;

import com.javacalc.exceptions.DivisionByZeroException;
import com.javacalc.exceptions.InvalidExpressionException;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for all supported operators with extensibility support.
 */
public class OperatorRegistry {
    private final Map<Character, Operator> operators;

    public OperatorRegistry() {
        this.operators = new HashMap<>();
        registerDefaultOperators();
    }

    /**
     * Registers all default mathematical operators.
     */
    private void registerDefaultOperators() {
        // Basic arithmetic operators
        register(new Operator('+', 1, (a, b) -> a + b));
        register(new Operator('-', 1, (a, b) -> a - b));
        register(new Operator('*', 2, (a, b) -> a * b));
        register(new Operator('/', 2, (a, b) -> {
            if (b == 0.0) {
                throw new DivisionByZeroException();
            }
            return a / b;
        }));

        // Additional operators
        register(new Operator('^', 3, Math::pow));  // Power
        register(new Operator('%', 2, (a, b) -> {    // Modulo
            if (b == 0.0) {
                throw new DivisionByZeroException();
            }
            return a % b;
        }));
    }

    /**
     * Registers a new operator.
     *
     * @param operator the operator to register
     */
    public void register(Operator operator) {
        operators.put(operator.getSymbol(), operator);
    }

    /**
     * Gets an operator by its symbol.
     *
     * @param symbol the operator symbol
     * @return the operator
     * @throws InvalidExpressionException if the operator is not found
     */
    public Operator getOperator(char symbol) {
        Operator op = operators.get(symbol);
        if (op == null) {
            throw new InvalidExpressionException("ERROR: Unknown operator '" + symbol + "'");
        }
        return op;
    }

    /**
     * Checks if a character is a registered operator.
     *
     * @param symbol the character to check
     * @return true if it's a registered operator
     */
    public boolean isOperator(char symbol) {
        return operators.containsKey(symbol);
    }

    /**
     * Gets the precedence of an operator.
     *
     * @param symbol the operator symbol
     * @return the precedence value
     */
    public int getPrecedence(char symbol) {
        return operators.getOrDefault(symbol, new Operator(symbol, 0, (a, b) -> 0)).getPrecedence();
    }
}
