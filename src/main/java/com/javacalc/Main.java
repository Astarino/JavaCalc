package com.javacalc;

import com.javacalc.calculator.Calculator;
import com.javacalc.exceptions.CalculatorException;

import java.util.Scanner;

/**
 * Main entry point for the JavaCalc calculator application.
 * Provides an interactive REPL (Read-Eval-Print Loop) for evaluating mathematical expressions.
 */
public class Main {
    private static final String WELCOME_MESSAGE = """
            ╔═══════════════════════════════════════════════════════════════╗
            ║                     JavaCalc Calculator                       ║
            ║         Mathematical Expression Evaluator v2.0               ║
            ╚═══════════════════════════════════════════════════════════════╝

            Choose a mode: POST (postfix notation) or INFIX (standard notation)

            Supported operators: + - * / ^ (power) % (modulo)
            Variables: Any name starting with a letter (e.g., x, y, result)

            Commands:
              POST       - Switch to postfix mode
              INFIX      - Switch to infix mode
              CLEAR      - Clear all variables
              HELP       - Show this help message
              <empty>    - Exit the calculator

            Examples:
              INFIX: 2 * (3 + 4) ^ 2
              INFIX: x = 5 + 3
              POST:  5 3 + 2 *
            """;

    private static final String HELP_MESSAGE = """

            === JavaCalc Help ===

            INFIX MODE (standard mathematical notation):
              - Write expressions naturally: 2 + 3 * 4
              - Use parentheses for grouping: (2 + 3) * 4
              - Assign variables: x = 10
              - Use variables: x * 2 + 5
              - Power: 2 ^ 3 = 8
              - Modulo: 10 % 3 = 1

            POSTFIX MODE (Reverse Polish Notation):
              - Operators come after operands: 2 3 +
              - No parentheses needed: 2 3 4 * +
              - Assign variables: x 10 =
              - Use variables: x 2 * 5 +

            Operators (by precedence):
              ^  Power (highest)
              *  Multiplication
              /  Division
              %  Modulo
              +  Addition
              -  Subtraction (lowest)
            """;

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println(WELCOME_MESSAGE);
        System.out.println("Current mode: " + calculator.getMode());
        System.out.println();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Handle empty input (exit)
            if (input.isEmpty()) {
                System.out.println("Goodbye!");
                break;
            }

            // Handle mode switches
            if (input.equalsIgnoreCase("POST") || input.equalsIgnoreCase("POSTFIX")) {
                calculator.setMode(Calculator.Mode.POSTFIX);
                System.out.println("Mode changed to POSTFIX (Reverse Polish Notation)");
                System.out.println("Current mode: " + calculator.getMode());
                continue;
            } else if (input.equalsIgnoreCase("INFIX")) {
                calculator.setMode(Calculator.Mode.INFIX);
                System.out.println("Mode changed to INFIX (standard notation)");
                System.out.println("Current mode: " + calculator.getMode());
                continue;
            } else if (input.equalsIgnoreCase("HELP")) {
                System.out.println(HELP_MESSAGE);
                continue;
            } else if (input.equalsIgnoreCase("CLEAR")) {
                calculator.getVariableManager().clear();
                System.out.println("All variables cleared");
                continue;
            }

            // Evaluate expression
            try {
                if (input.contains("=")) {
                    // Variable assignment
                    double result = calculator.assignVariable(input);
                    String varName = input.split("=")[0].trim();
                    System.out.println(varName + " = " + formatResult(result));
                } else {
                    // Expression evaluation
                    double result = calculator.evaluate(input);
                    System.out.println("Result: " + formatResult(result));
                }
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: " + (e.getMessage() != null ? e.getMessage() : "Unknown error occurred"));
            }
        }

        scanner.close();
    }

    /**
     * Formats a result for display, showing integers without decimal points.
     *
     * @param result the result to format
     * @return formatted string representation
     */
    private static String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%.6f", result).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }
}
