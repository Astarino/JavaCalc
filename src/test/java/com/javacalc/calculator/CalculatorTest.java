package com.javacalc.calculator;

import com.javacalc.exceptions.DivisionByZeroException;
import com.javacalc.exceptions.InvalidExpressionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testDefaultMode() {
        assertEquals(Calculator.Mode.POSTFIX, calculator.getMode());
    }

    @Test
    void testSetMode() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(Calculator.Mode.INFIX, calculator.getMode());
    }

    @Test
    void testPostfixEvaluation() {
        calculator.setMode(Calculator.Mode.POSTFIX);
        assertEquals(7.0, calculator.evaluate("3 4 +"));
    }

    @Test
    void testInfixEvaluation() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(7.0, calculator.evaluate("3 + 4"));
    }

    @Test
    void testInfixWithParentheses() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(14.0, calculator.evaluate("2 * (3 + 4)"));
    }

    @Test
    void testInfixPrecedence() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(14.0, calculator.evaluate("2 + 3 * 4"));
    }

    @Test
    void testPowerOperation() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(8.0, calculator.evaluate("2 ^ 3"));
    }

    @Test
    void testModuloOperation() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(1.0, calculator.evaluate("10 % 3"));
    }

    @Test
    void testVariableAssignment() {
        calculator.setMode(Calculator.Mode.INFIX);
        double result = calculator.assignVariable("x = 5 + 3");
        assertEquals(8.0, result);
        assertEquals(8.0, calculator.getVariableManager().getVariable("x"));
    }

    @Test
    void testVariableUsage() {
        calculator.setMode(Calculator.Mode.INFIX);
        calculator.assignVariable("x = 10");
        assertEquals(20.0, calculator.evaluate("x * 2"));
    }

    @Test
    void testMultipleVariables() {
        calculator.setMode(Calculator.Mode.INFIX);
        calculator.assignVariable("x = 5");
        calculator.assignVariable("y = 3");
        assertEquals(8.0, calculator.evaluate("x + y"));
    }

    @Test
    void testInvalidVariableName() {
        assertThrows(InvalidExpressionException.class, () -> {
            calculator.assignVariable("123 = 5");
        });
    }

    @Test
    void testEmptyAssignment() {
        assertThrows(InvalidExpressionException.class, () -> {
            calculator.assignVariable("x = ");
        });
    }

    @Test
    void testDivisionByZero() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertThrows(DivisionByZeroException.class, () -> {
            calculator.evaluate("10 / 0");
        });
    }

    @Test
    void testFloatingPointArithmetic() {
        calculator.setMode(Calculator.Mode.INFIX);
        assertEquals(2.5, calculator.evaluate("5 / 2"));
    }

    @Test
    void testComplexExpression() {
        calculator.setMode(Calculator.Mode.INFIX);
        calculator.assignVariable("x = 2");
        calculator.assignVariable("y = 3");
        assertEquals(49.0, calculator.evaluate("(x + y) ^ 2"));
    }

    @Test
    void testDynamicVariableNames() {
        calculator.setMode(Calculator.Mode.INFIX);
        calculator.assignVariable("result = 42");
        calculator.assignVariable("myVar = 10");
        calculator.assignVariable("x1 = 5");
        assertEquals(57.0, calculator.evaluate("result + myVar + x1"));
    }
}
