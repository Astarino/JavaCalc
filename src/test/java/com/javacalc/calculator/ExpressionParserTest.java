package com.javacalc.calculator;

import com.javacalc.exceptions.InvalidExpressionException;
import com.javacalc.operators.OperatorRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionParserTest {
    private ExpressionParser parser;

    @BeforeEach
    void setUp() {
        parser = new ExpressionParser(new OperatorRegistry());
    }

    @Test
    void testSimpleAddition() {
        String result = parser.infixToPostfix("2 + 3");
        assertEquals("2 3 +", result);
    }

    @Test
    void testMultipleOperators() {
        String result = parser.infixToPostfix("2 + 3 * 4");
        assertEquals("2 3 4 * +", result);
    }

    @Test
    void testParentheses() {
        String result = parser.infixToPostfix("(2 + 3) * 4");
        assertEquals("2 3 + 4 *", result);
    }

    @Test
    void testNestedParentheses() {
        String result = parser.infixToPostfix("((2 + 3) * 4) / 5");
        assertEquals("2 3 + 4 * 5 /", result);
    }

    @Test
    void testPowerOperator() {
        String result = parser.infixToPostfix("2 ^ 3");
        assertEquals("2 3 ^", result);
    }

    @Test
    void testComplexExpression() {
        String result = parser.infixToPostfix("2 * 3 + 4 * 5");
        assertEquals("2 3 * 4 5 * +", result);
    }

    @Test
    void testWithVariables() {
        String result = parser.infixToPostfix("x + y * 2");
        assertEquals("x y 2 * +", result);
    }

    @Test
    void testDecimalNumbers() {
        String result = parser.infixToPostfix("3.14 + 2.5");
        assertEquals("3.14 2.5 +", result);
    }

    @Test
    void testMismatchedOpeningParenthesis() {
        assertThrows(InvalidExpressionException.class, () -> {
            parser.infixToPostfix("(2 + 3");
        });
    }

    @Test
    void testMismatchedClosingParenthesis() {
        assertThrows(InvalidExpressionException.class, () -> {
            parser.infixToPostfix("2 + 3)");
        });
    }

    @Test
    void testInvalidToken() {
        assertThrows(InvalidExpressionException.class, () -> {
            parser.infixToPostfix("2 & 3");
        });
    }

    @Test
    void testModuloOperator() {
        String result = parser.infixToPostfix("10 % 3");
        assertEquals("10 3 %", result);
    }
}
