package com.javacalc.calculator;

import com.javacalc.exceptions.DivisionByZeroException;
import com.javacalc.exceptions.InvalidExpressionException;
import com.javacalc.operators.OperatorRegistry;
import com.javacalc.variables.VariableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {
    private ExpressionEvaluator evaluator;
    private VariableManager variableManager;

    @BeforeEach
    void setUp() {
        OperatorRegistry operatorRegistry = new OperatorRegistry();
        variableManager = new VariableManager();
        evaluator = new ExpressionEvaluator(operatorRegistry, variableManager);
    }

    @Test
    void testSimpleAddition() {
        assertEquals(5.0, evaluator.evaluatePostfix("2 3 +"));
    }

    @Test
    void testSimpleSubtraction() {
        assertEquals(2.0, evaluator.evaluatePostfix("5 3 -"));
    }

    @Test
    void testSimpleMultiplication() {
        assertEquals(15.0, evaluator.evaluatePostfix("3 5 *"));
    }

    @Test
    void testSimpleDivision() {
        assertEquals(4.0, evaluator.evaluatePostfix("12 3 /"));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(DivisionByZeroException.class, () -> {
            evaluator.evaluatePostfix("10 0 /");
        });
    }

    @Test
    void testPowerOperation() {
        assertEquals(8.0, evaluator.evaluatePostfix("2 3 ^"));
    }

    @Test
    void testModuloOperation() {
        assertEquals(1.0, evaluator.evaluatePostfix("10 3 %"));
    }

    @Test
    void testComplexExpression() {
        // (2 + 3) * 4 = 20
        assertEquals(20.0, evaluator.evaluatePostfix("2 3 + 4 *"));
    }

    @Test
    void testWithVariables() {
        variableManager.setVariable("x", 10.0);
        variableManager.setVariable("y", 5.0);
        assertEquals(15.0, evaluator.evaluatePostfix("x y +"));
    }

    @Test
    void testUndefinedVariableInitializedToZero() {
        assertEquals(5.0, evaluator.evaluatePostfix("5 x +"));
        assertTrue(variableManager.hasVariable("x"));
        assertEquals(0.0, variableManager.getVariable("x"));
    }

    @Test
    void testDecimalNumbers() {
        assertEquals(5.64, evaluator.evaluatePostfix("3.14 2.5 +"), 0.001);
    }

    @Test
    void testNegativeNumbers() {
        assertEquals(-2.0, evaluator.evaluatePostfix("3 -5 +"));
    }

    @Test
    void testInsufficientOperands() {
        assertThrows(InvalidExpressionException.class, () -> {
            evaluator.evaluatePostfix("2 +");
        });
    }

    @Test
    void testTooManyOperands() {
        assertThrows(InvalidExpressionException.class, () -> {
            evaluator.evaluatePostfix("2 3 4 +");
        });
    }

    @Test
    void testInvalidToken() {
        assertThrows(InvalidExpressionException.class, () -> {
            evaluator.evaluatePostfix("2 3 &");
        });
    }

    @Test
    void testFloatingPointDivision() {
        assertEquals(2.5, evaluator.evaluatePostfix("5 2 /"));
    }
}
