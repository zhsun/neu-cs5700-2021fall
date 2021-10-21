package hw;

import java.util.Deque;
import java.util.LinkedList;

public class Calculator {
  public static String eval(String expression) {
    Deque<Integer> numbers = new LinkedList<>();
    Deque<String> ops = new LinkedList<>();
    int start = 0;
    while (hasToken(expression, start)) {
      String token = getToken(expression, start);
      start += token.length();
      if (isOp(token)) {
        ops.add(token);
      } else {
        numbers.add(Integer.valueOf(token));
      }
    }
    while (!ops.isEmpty()) {
      Integer a = numbers.remove();
      Integer b = numbers.remove();
      String op = ops.remove();
      if (op.equals("+")) {
        numbers.addFirst(a + b);
      } else {
        numbers.addFirst(a - b);
      }
    }
    return String.valueOf(numbers.remove());
  }

  private static boolean hasToken(String expression, int start) {
    return start < expression.length();
  }

  private static String getToken(String expression, int start) {
    if (expression.charAt(start) == '+') {
      return "+";
    }
    if (expression.charAt(start) == '-') {
      return "-";
    }
    StringBuilder sb = new StringBuilder();
    while (start < expression.length()) {
      char c = expression.charAt(start++);
      if (c == '+' || c == '-') {
        break;
      }
      sb.append(c);
    }
    return sb.toString();
  }

  private static boolean isOp(String token) {
    return token.equals("+") || token.equals("-");
  }
}
