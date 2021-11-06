package concurrency;

import java.math.BigInteger;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class ThreadJoinExample {
  public static void main(String[] args) throws InterruptedException {
    List<Long> numbers = Arrays.asList(0L, 3435L, 35435L, 23L, 5566L, 999999L);
    System.out.println("Numbers to compute factorial: " + numbers);

    List<FactorialThread> ts = new ArrayList<>();
    for (long n : numbers) ts.add(new FactorialThread(n));
    for (Thread t : ts) {
      // Avoid long running thread blocking application exit.
      t.setDaemon(true);
      t.start();
    }
    checkResult(numbers, ts);

    System.out.println("\nJoining all factorial threads\n");
    for (Thread t : ts) {
      t.join(2000);  // Max to wait for 2s
    }
    checkResult(numbers, ts);
  }

  public static void checkResult(List<Long> numbers, List<FactorialThread> ts) {
    for (int i = 0; i < numbers.size(); i++) {
      FactorialThread t = ts.get(i);
      if (t.isFinished()) {
        System.out.println(numbers.get(i) + "! is completed");
      } else {
        System.out.println(numbers.get(i) + "! still in progress");
      }
    }
  }

  public static class FactorialThread extends Thread {
    private long n;
    private BigInteger res;
    private boolean finished = false;

    public FactorialThread(long n) {
      assert n >= 0;
      this.n = n;
      this.res = BigInteger.ZERO;
      this.finished = false;
    }

    @Override
    public void run() {
      this.res = compute();
      this.finished = true;
    }

    private BigInteger compute() {
      BigInteger r = BigInteger.ONE;
      for (long i = n; i > 0; i--) {
        r = r.multiply(new BigInteger(Long.toString(i)));
      }
      return r;
    }

    public boolean isFinished() {
      return finished;
    }

    public BigInteger getResult() {
      return res;
    }
  }
}
