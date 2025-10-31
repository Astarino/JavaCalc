package com.javacalc.calculator;

import com.javacalc.exceptions.InvalidExpressionException;
import com.javacalc.operators.OperatorRegistry;
import com.javacalc.variables.VariableManager;

import java.util.Stack;

/**
 * Parser for converting infix expressions to postfix notation using the Shunting Yard algorithm.
 * Supports operators, parentheses, numbers, and variables.
 */
public class ExpressionParser {
    private final OperatorRegistry operatorRegistry;

    public ExpressionParser(OperatorRegistry operatorRegistry) {
        this.operatorRegistry = operatorRegistry;
    }

    /**
     * Converts an infix expression to postfix notation (Reverse Polish Notation).
     * Uses Dijkstra's Shunting Yard algorithm.
     *
     * @param expression the infix expression
     * @return the postfix expression
     * @throws InvalidExpressionException if the expression is malformed
     */
    public String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        // Tokenize the expression
        String[] tokens = tokenize(expression);

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            // Handle numbers (including negative numbers)
            if (isNumber(token)) {
                output.append(token).append(" ");
            }
            // Handle variables
            else if (VariableManager.isValidVariableName(token)) {
                output.append(token).append(" ");
            }
            // Handle opening parenthesis
            else if (token.equals("(")) {
                stack.push('(');
            }
            // Handle closing parenthesis
            else if (token.equals(")")) {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(" ");
                }
                if (stack.isEmpty()) {
                    throw new InvalidExpressionException("ERROR: Mismatched parentheses");
                }
                stack.pop(); // Remove the '('
            }
            // Handle operators
            else if (token.length() == 1 && operatorRegistry.isOperator(token.charAt(0))) {
                char currentOp = token.charAt(0);
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       operatorRegistry.isOperator(stack.peek()) &&
                       operatorRegistry.getPrecedence(stack.peek()) >= operatorRegistry.getPrecedence(currentOp)) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(currentOp);
            } else {
                throw new InvalidExpressionException("ERROR: Invalid token '" + token + "'");
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new InvalidExpressionException("ERROR: Mismatched parentheses");
            }
            output.append(stack.pop()).append(" ");
        }

        return output.toString().trim();
    }

    /**
     * Tokenizes an expression by splitting on operators and parentheses.
     *
     * @param expression the expression to tokenize
     * @return array of tokens
     */
    private String[] tokenize(String expression) {
        // Remove all whitespace and split by operators/parentheses while keeping delimiters
        return expression.replaceAll("\\s+", "")
                .split("(?<=[-+*/%^()])|(?=[-+*/%^()])");
    }

    /**
     * Checks if a token is a valid number (integer or decimal, possibly negative).
     *
     * @param token the token to check
     * @return true if the token is a number
     */
    private boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }
}
