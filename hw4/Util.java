package hw;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
  public static void log(String msg) {
    System.out.println(msg);
  }

  public static void log(byte[] msg) {
    StringBuilder sb = new StringBuilder();
    for (byte b : msg) {
      sb.append(String.format("%02X", b));
    }
    log(sb.toString());
  }

  // Introduce random bit error to data.
  public static byte[] randomBitError(byte[] data) {
    int i = ThreadLocalRandom.current().nextInt(data.length);
    data[i] = (byte) ~data[i];
    return data;
  }

  public static void main(String... args) {
    if (args.length > 0) {
      for (String arg : args) {
        log(arg + " => " + new String(randomBitError(arg.getBytes())));
      }
      return;
    }
    byte[] data = new byte[3];
    Arrays.fill(data, (byte) 0xFF);
    log(data);
    randomBitError(data);
    log(data);

    Arrays.fill(data, (byte) 0x45);
    log(data);
    randomBitError(data);
    log(data);
  }
}
