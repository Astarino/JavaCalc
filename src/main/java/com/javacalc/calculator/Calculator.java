package com.javacalc.calculator;

import com.javacalc.exceptions.InvalidExpressionException;
import com.javacalc.operators.OperatorRegistry;
import com.javacalc.variables.VariableManager;

/**
 * Main calculator class that coordinates expression parsing and evaluation.
 * Supports both infix and postfix notation modes.
 */
public class Calculator {
    /**
     * Calculation mode enumeration.
     */
    public enum Mode {
        POSTFIX,  // Reverse Polish Notation (RPN)
        INFIX     // Standard mathematical notation
    }

    private final OperatorRegistry operatorRegistry;
    private final VariableManager variableManager;
    private final ExpressionParser parser;
    private final ExpressionEvaluator evaluator;
    private Mode mode;

    /**
     * Creates a new Calculator with default mode (POSTFIX).
     */
    public Calculator() {
        this.operatorRegistry = new OperatorRegistry();
        this.variableManager = new VariableManager();
        this.parser = new ExpressionParser(operatorRegistry);
        this.evaluator = new ExpressionEvaluator(operatorRegistry, variableManager);
        this.mode = Mode.POSTFIX;
    }

    /**
     * Sets the calculator mode.
     *
     * @param mode the mode to use (POSTFIX or INFIX)
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Gets the current calculator mode.
     *
     * @return the current mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Evaluates an expression in the current mode.
     *
     * @param expression the expression to evaluate
     * @return the result of the evaluation
     * @throws InvalidExpressionException if the expression is invalid
     */
    public double evaluate(String expression) {
        String postfixExpression;

        if (mode == Mode.INFIX) {
            postfixExpression = parser.infixToPostfix(expression);
        } else {
            postfixExpression = expression;
        }

        return evaluator.evaluatePostfix(postfixExpression);
    }

    /**
     * Handles variable assignment expressions (e.g., "x = 5 + 3").
     *
     * @param expression the assignment expression
     * @return the assigned value
     * @throws InvalidExpressionException if the assignment is invalid
     */
    public double assignVariable(String expression) {
        String[] parts = expression.split("=", 2);

        if (parts.length != 2) {
            throw new InvalidExpressionException("ERROR: Invalid assignment expression");
        }

        String variableName = parts[0].trim();
        String valueExpression = parts[1].trim();

        if (!VariableManager.isValidVariableName(variableName)) {
            throw new InvalidExpressionException("ERROR: Invalid variable name '" + variableName + "'. " +
                    "Variable names must start with a letter and contain only letters and digits.");
        }

        if (valueExpression.isEmpty()) {
            throw new InvalidExpressionException("ERROR: Right side of assignment cannot be empty");
        }

        double value = evaluate(valueExpression);
        variableManager.setVariable(variableName, value);

        return value;
    }

    /**
     * Gets the variable manager for accessing variable values.
     *
     * @return the variable manager
     */
    public VariableManager getVariableManager() {
        return variableManager;
    }

    /**
     * Gets the operator registry for accessing operator information.
     *
     * @return the operator registry
     */
    public OperatorRegistry getOperatorRegistry() {
        return operatorRegistry;
    }
}
