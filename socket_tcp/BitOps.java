package demo;

public class BitOps {
  public static void main(String... args) {
    System.out.println("x");
    int x = 67;
    show(x);

    System.out.println("y");
    int y = 11;
    show(y);

    System.out.println("x & y");
    show(x & y);

    System.out.println("x | y");
    show(x | y);

    System.out.println("~x");
    show(~x);

    System.out.println("x ^ y");
    show(x ^ y);

    System.out.println("x << 1");
    show(x << 1);

    System.out.println("x >> 1");
    show(x >> 1);
  }

  public static void show(int n) {
    System.out.println("Decimal: " + n);
    System.out.println("Binary: " + Integer.toBinaryString(n));
    System.out.println("Hex: " + Integer.toHexString(n));
  }
}
