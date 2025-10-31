package com.javacalc.variables;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages variables and their values for calculator expressions.
 * Supports dynamic variable creation and retrieval.
 */
public class VariableManager {
    private final Map<String, Double> variables;

    public VariableManager() {
        this.variables = new HashMap<>();
    }

    /**
     * Sets a variable to a specific value.
     *
     * @param name the variable name
     * @param value the value to set
     */
    public void setVariable(String name, double value) {
        variables.put(name, value);
    }

    /**
     * Gets the value of a variable.
     *
     * @param name the variable name
     * @return the variable value, or 0.0 if not defined
     */
    public double getVariable(String name) {
        return variables.getOrDefault(name, 0.0);
    }

    /**
     * Checks if a variable is defined.
     *
     * @param name the variable name
     * @return true if the variable exists
     */
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    /**
     * Checks if a string is a valid variable name.
     * Variables must start with a letter and contain only letters and digits.
     *
     * @param token the token to check
     * @return true if the token is a valid variable name
     */
    public static boolean isValidVariableName(String token) {
        return token.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    /**
     * Clears all variables.
     */
    public void clear() {
        variables.clear();
    }

    /**
     * Gets all variables as an immutable map.
     *
     * @return a copy of all variables
     */
    public Map<String, Double> getAllVariables() {
        return new HashMap<>(variables);
    }
}
