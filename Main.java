import java.util.*;

public class Main{
    private static final Map<String, Integer> varies = new HashMap<>();
    private static String mode;

    static {
        for (int i = 1; i <= 5; i++) {
            varies.put("x" + i, 0);
        }
        mode = "POST";
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите режим: POST (постфиксная запись) или INFIX (инфиксная запись)");
        System.out.println("Текущий режим: " + mode);

        while (true) {
            System.out.println("Введите выражение или смените режим (POST / INFIX):");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("POST")) {
                mode = "POST";
                System.out.println("Режим изменён на постфиксную запись.");
                System.out.println("Текущий режим: " + mode);
                continue;
            } else if (input.equalsIgnoreCase("INFIX")) {
                mode = "INFIX";
                System.out.println("Режим изменён на инфиксную запись.");
                System.out.println("Текущий режим: " + mode);
                continue;
            } else if (input.isEmpty()) {
                System.out.println("Finalized");
                break;
            }

            try {
                if (input.contains("=")) {
                    signCall(input);
                } else {
                    int result;
                    if (mode.equals("POST")) {
                        result = evaluatePost(input);
                    } else {
                        String postfix = infixToPostfix(input);
                        result = evaluatePost(postfix);
                    }
                    System.out.println("Результат: " + result);
                }
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage() + " - большое значение");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }

    private static String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        Map<Character, Integer> precedence = Map.of(
                '+', 1, '-', 1, '*', 2, '/', 2, '(', 0
        );

        String[] tokens = expression.replaceAll("\\s+", "").split("(?<=[-+*/()])|(?=[-+*/()])");

        for (String token : tokens) {
            if (token.matches("-?\\d+") || token.matches("x[1-5]")) {
                output.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push('(');
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(" ");
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("ERROR: Некорректное расположение скобок.");
                }
                stack.pop();
            } else if (precedence.containsKey(token.charAt(0))) {
                while (!stack.isEmpty() && precedence.get(stack.peek()) >= precedence.get(token.charAt(0))) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token.charAt(0));
            } else {
                throw new IllegalArgumentException("ERROR: Недопустимый символ '" + token + "'");
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new IllegalArgumentException("ERROR: Некорректное расположение скобок.");
            }
            output.append(stack.pop()).append(" ");
        }

        return output.toString().trim();
    }

    private static void signCall(String input) {
        String[] units = input.split("=", 2);
        String varName = units[0].trim();
        String utterance = units[1].trim();

        if (!varName.matches("x[1-5]")) {
            throw new IllegalArgumentException("ERROR: допустимы только переменные x1-x5.");
        }

        if (utterance.isEmpty()) {
            throw new IllegalArgumentException("ERROR: правая часть выражения не может быть пустой.");
        }

        int val;
        if (mode.equals("POST")) {
            val = evaluatePost(utterance);
        } else {
            String postfix = infixToPostfix(utterance);
            val = evaluatePost(postfix);
        }

        varies.put(varName, val);
        System.out.println(varName + " = " + val);
    }

    private static int evaluatePost(String utterance) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = utterance.split(" ");

        for (String token : tokens) {
            if (token.matches("x[6-9]|x\\d{2,}")) {
                throw new IllegalArgumentException("ERROR: допустимы только переменные x1-x5.");
            }

            if (!token.matches("-?\\d+|x[1-5]|[+\\-*/]")) {
                throw new IllegalArgumentException("ERROR: введены недопустимые символы '" + token + "'");
            }

            if (token.matches("-?\\d+")) {
                stack.push(Integer.parseInt(token));
            } else if (varies.containsKey(token)) {
                stack.push(varies.get(token));
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("ERROR: недостаточно элементов");
                }
                int b = stack.pop();
                int a = stack.pop();

                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("ERROR: деление на ноль");
                        }
                        stack.push(a / b);
                        break;
                    default:
                        throw new IllegalArgumentException("ERROR: неизвестный оператор '" + token + "'");
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("ERROR: неверное выражение");
        }

        return stack.pop();
    }
}