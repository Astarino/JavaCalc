package com.javacalc.operators;

import com.javacalc.exceptions.DivisionByZeroException;
import com.javacalc.exceptions.InvalidExpressionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperatorRegistryTest {
    private OperatorRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new OperatorRegistry();
    }

    @Test
    void testBasicOperators() {
        assertTrue(registry.isOperator('+'));
        assertTrue(registry.isOperator('-'));
        assertTrue(registry.isOperator('*'));
        assertTrue(registry.isOperator('/'));
        assertTrue(registry.isOperator('^'));
        assertTrue(registry.isOperator('%'));
    }

    @Test
    void testOperatorPrecedence() {
        assertEquals(1, registry.getPrecedence('+'));
        assertEquals(1, registry.getPrecedence('-'));
        assertEquals(2, registry.getPrecedence('*'));
        assertEquals(2, registry.getPrecedence('/'));
        assertEquals(3, registry.getPrecedence('^'));
        assertEquals(2, registry.getPrecedence('%'));
    }

    @Test
    void testAdditionOperator() {
        Operator op = registry.getOperator('+');
        assertEquals(15.0, op.apply(10.0, 5.0));
    }

    @Test
    void testSubtractionOperator() {
        Operator op = registry.getOperator('-');
        assertEquals(5.0, op.apply(10.0, 5.0));
    }

    @Test
    void testMultiplicationOperator() {
        Operator op = registry.getOperator('*');
        assertEquals(50.0, op.apply(10.0, 5.0));
    }

    @Test
    void testDivisionOperator() {
        Operator op = registry.getOperator('/');
        assertEquals(2.0, op.apply(10.0, 5.0));
    }

    @Test
    void testDivisionByZero() {
        Operator op = registry.getOperator('/');
        assertThrows(DivisionByZeroException.class, () -> op.apply(10.0, 0.0));
    }

    @Test
    void testPowerOperator() {
        Operator op = registry.getOperator('^');
        assertEquals(8.0, op.apply(2.0, 3.0));
    }

    @Test
    void testModuloOperator() {
        Operator op = registry.getOperator('%');
        assertEquals(1.0, op.apply(10.0, 3.0));
    }

    @Test
    void testModuloByZero() {
        Operator op = registry.getOperator('%');
        assertThrows(DivisionByZeroException.class, () -> op.apply(10.0, 0.0));
    }

    @Test
    void testUnknownOperator() {
        assertThrows(InvalidExpressionException.class, () -> registry.getOperator('&'));
    }

    @Test
    void testCustomOperatorRegistration() {
        Operator customOp = new Operator('@', 4, (a, b) -> a * 2 + b);
        registry.register(customOp);
        assertTrue(registry.isOperator('@'));
        assertEquals(4, registry.getPrecedence('@'));
    }
}
