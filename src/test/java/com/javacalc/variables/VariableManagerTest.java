package com.javacalc.variables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VariableManagerTest {
    private VariableManager variableManager;

    @BeforeEach
    void setUp() {
        variableManager = new VariableManager();
    }

    @Test
    void testSetAndGetVariable() {
        variableManager.setVariable("x", 42.0);
        assertEquals(42.0, variableManager.getVariable("x"));
    }

    @Test
    void testGetUndefinedVariable() {
        assertEquals(0.0, variableManager.getVariable("undefined"));
    }

    @Test
    void testHasVariable() {
        variableManager.setVariable("x", 10.0);
        assertTrue(variableManager.hasVariable("x"));
        assertFalse(variableManager.hasVariable("y"));
    }

    @Test
    void testClearVariables() {
        variableManager.setVariable("x", 10.0);
        variableManager.setVariable("y", 20.0);
        variableManager.clear();
        assertFalse(variableManager.hasVariable("x"));
        assertFalse(variableManager.hasVariable("y"));
    }

    @Test
    void testValidVariableNames() {
        assertTrue(VariableManager.isValidVariableName("x"));
        assertTrue(VariableManager.isValidVariableName("var123"));
        assertTrue(VariableManager.isValidVariableName("myVariable"));
        assertTrue(VariableManager.isValidVariableName("x1"));
    }

    @Test
    void testInvalidVariableNames() {
        assertFalse(VariableManager.isValidVariableName("123"));
        assertFalse(VariableManager.isValidVariableName(""));
        assertFalse(VariableManager.isValidVariableName("var-name"));
        assertFalse(VariableManager.isValidVariableName("var name"));
    }

    @Test
    void testGetAllVariables() {
        variableManager.setVariable("x", 10.0);
        variableManager.setVariable("y", 20.0);
        var allVars = variableManager.getAllVariables();
        assertEquals(2, allVars.size());
        assertEquals(10.0, allVars.get("x"));
        assertEquals(20.0, allVars.get("y"));
    }
}
