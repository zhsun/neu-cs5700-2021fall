package demo;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class StringAndByte {
  public static void main(String... args) {
    String x = "Hello World!";
    System.out.println(x);
    System.out.println(x.length());
    System.out.println(x.getBytes().length);
    System.out.println(Arrays.toString(x.getBytes()));
    System.out.println();

    String y = "Hello 世界!";
    System.out.println(y);
    System.out.println(y.length());
    System.out.println(y.getBytes().length);
    System.out.println(Arrays.toString(y.getBytes()));
    System.out.println(Arrays.toString(y.getBytes(UTF_8)));
    System.out.println();

    System.out.println(new String(x.getBytes()));
    System.out.println(new String(y.getBytes()));
    System.out.println(new String(y.getBytes(UTF_8)));
    System.out.println(new String(y.getBytes(US_ASCII)));
    System.out.println(new String(y.getBytes(UTF_8), US_ASCII));
    System.out.println(new String(y.getBytes(UTF_8), UTF_8));
  }
}
