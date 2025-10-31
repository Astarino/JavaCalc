# JavaCalc - Mathematical Expression Calculator

A powerful, extensible command-line calculator supporting both **Infix** (standard mathematical notation) and **Postfix** (Reverse Polish Notation) expression evaluation.

## Features

- **Dual Notation Support**: Switch between Infix and Postfix modes
- **Floating-Point Arithmetic**: Precise decimal calculations
- **Dynamic Variables**: Create and use any variable name (not limited to x1-x5)
- **Rich Operator Set**: `+`, `-`, `*`, `/`, `^` (power), `%` (modulo)
- **Extensible Architecture**: Easy to add new operators
- **Comprehensive Error Handling**: Clear, descriptive error messages
- **Full Test Coverage**: JUnit 5 test suite included
- **Maven Build**: Professional build configuration

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+ (for building from source)

### Running the Application

**Option 1: Using Maven**
```bash
mvn clean package
java -jar target/javacalc.jar
```

**Option 2: Direct Compilation**
```bash
# Compile
javac -d bin src/main/java/com/javacalc/**/*.java src/main/java/com/javacalc/*.java

# Run
java -cp bin com.javacalc.Main
```

## Usage Examples

### Infix Mode (Standard Notation)

```
> INFIX
Mode changed to INFIX (standard notation)

> 2 + 3 * 4
Result: 14

> (2 + 3) * 4
Result: 20

> 2 ^ 3
Result: 8

> 10 % 3
Result: 1

> x = 5 + 3
x = 8

> y = x * 2
y = 16

> (x + y) / 2
Result: 12
```

### Postfix Mode (Reverse Polish Notation)

```
> POST
Mode changed to POSTFIX (Reverse Polish Notation)

> 2 3 +
Result: 5

> 2 3 + 4 *
Result: 20

> 5 3 + 2 *
Result: 16

> x 10 =
x = 10

> x 2 * 5 +
Result: 25
```

## Supported Operators

| Operator | Description | Precedence | Example (Infix) | Example (Postfix) |
|----------|-------------|------------|-----------------|-------------------|
| `^` | Power | 3 (highest) | `2 ^ 3` = 8 | `2 3 ^` = 8 |
| `*` | Multiplication | 2 | `3 * 4` = 12 | `3 4 *` = 12 |
| `/` | Division | 2 | `10 / 2` = 5 | `10 2 /` = 5 |
| `%` | Modulo | 2 | `10 % 3` = 1 | `10 3 %` = 1 |
| `+` | Addition | 1 | `2 + 3` = 5 | `2 3 +` = 5 |
| `-` | Subtraction | 1 (lowest) | `5 - 3` = 2 | `5 3 -` = 2 |

## Commands

- `INFIX` - Switch to infix mode
- `POST` or `POSTFIX` - Switch to postfix mode
- `HELP` - Show help message
- `CLEAR` - Clear all variables
- *Empty input* - Exit the calculator

## Variable Names

Variables can have any name that:
- Starts with a letter (a-z, A-Z)
- Contains only letters and digits
- Examples: `x`, `y`, `result`, `var123`, `myVariable`

## Architecture

The project follows clean architecture principles with clear separation of concerns:

```
src/main/java/com/javacalc/
├── Main.java                      # Entry point & REPL
├── calculator/
│   ├── Calculator.java            # Main calculator facade
│   ├── ExpressionParser.java      # Infix to Postfix converter
│   └── ExpressionEvaluator.java   # Postfix evaluator
├── operators/
│   ├── Operator.java              # Operator abstraction
│   └── OperatorRegistry.java      # Operator management
├── variables/
│   └── VariableManager.java       # Variable storage
└── exceptions/
    ├── CalculatorException.java   # Base exception
    ├── InvalidExpressionException.java
    ├── InvalidVariableException.java
    └── DivisionByZeroException.java
```

## Building and Testing

### Compile the Project
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Create Executable JAR
```bash
mvn clean package
```

The executable JAR will be created at `target/javacalc.jar`

### Run Tests with Coverage
```bash
mvn clean test
```

## Extending the Calculator

### Adding a New Operator

```java
// In OperatorRegistry.java, add to registerDefaultOperators():
register(new Operator('&', 2, (a, b) -> {
    // Your operation logic
    return result;
}));
```

### Creating Custom Variables

All variables are created dynamically - just assign them:
```
> myCustomVar = 42
myCustomVar = 42
```

## Error Handling

The calculator provides clear error messages:

```
> 10 / 0
ERROR: Division by zero is not allowed

> (2 + 3
ERROR: Mismatched parentheses

> 2 & 3
ERROR: Invalid token '&'

> 123abc = 5
ERROR: Invalid variable name '123abc'
```

## Algorithm Details

### Infix to Postfix Conversion
Uses **Dijkstra's Shunting Yard Algorithm** to convert infix expressions to postfix notation while respecting operator precedence and parentheses.

### Postfix Evaluation
Uses a **stack-based algorithm** for efficient evaluation:
1. Push operands onto the stack
2. Pop operands when encountering an operator
3. Apply the operator and push the result
4. Final stack value is the result

## Version History

### v2.0.0 (Current)
- Complete architectural refactoring
- Floating-point arithmetic support
- Dynamic variable names (removed x1-x5 limitation)
- Additional operators: `^` (power), `%` (modulo)
- Extensible operator system
- Custom exception hierarchy
- Maven build configuration
- Comprehensive JUnit 5 test suite
- Professional code documentation

### v1.0.0 (Legacy)
- Basic calculator with integer arithmetic
- Limited to x1-x5 variables
- Infix and Postfix mode support
- Basic operators: `+`, `-`, `*`, `/`

## License

This project is open source and available for educational purposes.

## Contributing

Contributions are welcome! Areas for enhancement:
- [ ] GUI interface
- [ ] Mathematical functions (sin, cos, log, etc.)
- [ ] Expression history/memory
- [ ] Multi-line expressions
- [ ] Configuration file support
- [ ] Localization (i18n)

## Author

JavaCalc Development Team

## Support

For issues, questions, or contributions, please open an issue in the repository.
