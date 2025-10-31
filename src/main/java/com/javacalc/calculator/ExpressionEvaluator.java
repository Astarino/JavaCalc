package com.javacalc.calculator;

import com.javacalc.exceptions.InvalidExpressionException;
import com.javacalc.operators.Operator;
import com.javacalc.operators.OperatorRegistry;
import com.javacalc.variables.VariableManager;

import java.util.Stack;

/**
 * Evaluates postfix (Reverse Polish Notation) expressions.
 * Supports floating-point arithmetic, variables, and extensible operators.
 */
public class ExpressionEvaluator {
    private final OperatorRegistry operatorRegistry;
    private final VariableManager variableManager;

    public ExpressionEvaluator(OperatorRegistry operatorRegistry, VariableManager variableManager) {
        this.operatorRegistry = operatorRegistry;
        this.variableManager = variableManager;
    }

    /**
     * Evaluates a postfix expression using a stack-based algorithm.
     *
     * @param postfixExpression the postfix expression to evaluate
     * @return the result of the evaluation
     * @throws InvalidExpressionException if the expression is malformed
     */
    public double evaluatePostfix(String postfixExpression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfixExpression.trim().split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            // Check if token is a number
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            }
            // Check if token is a variable
            else if (VariableManager.isValidVariableName(token)) {
                if (!variableManager.hasVariable(token)) {
                    // Initialize undefined variables to 0
                    variableManager.setVariable(token, 0.0);
                }
                stack.push(variableManager.getVariable(token));
            }
            // Token must be an operator
            else if (token.length() == 1 && operatorRegistry.isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new InvalidExpressionException("ERROR: Insufficient operands for operator '" + token + "'");
                }

                double b = stack.pop();
                double a = stack.pop();

                Operator operator = operatorRegistry.getOperator(token.charAt(0));
                double result = operator.apply(a, b);
                stack.push(result);
            } else {
                throw new InvalidExpressionException("ERROR: Invalid token '" + token + "'");
            }
        }

        if (stack.size() != 1) {
            throw new InvalidExpressionException("ERROR: Invalid expression - too many operands");
        }

        return stack.pop();
    }

    /**
     * Checks if a token is a valid number (integer or decimal, possibly negative).
     *
     * @param token the token to check
     * @return true if the token is a number
     */
    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
