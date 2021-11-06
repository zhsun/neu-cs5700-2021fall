package concurrency;

public class RaceConditionExample {
  public static void main(String[] args) throws InterruptedException {
    Counter counter = new Counter();
    IncrementThread incrementThread = new IncrementThread(counter);
    DecrementThread decrementThread = new DecrementThread(counter);

    incrementThread.start();
    decrementThread.start();

    incrementThread.join();
    decrementThread.join();

    System.out.println("Counter value is " + counter.get());
  }

  private static class Counter {
    private int i = 0;

    public void inc() { ++i; }  // Not atomic op.
    public void dec() { --i; }  // Not atomic op.
    public int get() { return i; }
  }

  private static class IncrementThread extends Thread {
    Counter counter;

    public IncrementThread(Counter counter) {
      this.counter = counter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 1000000; i++) {
        counter.inc();
      }
    }
  }

  private static class DecrementThread extends Thread {
    Counter counter;

    public DecrementThread(Counter counter) {
      this.counter = counter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 1000000; i++) {
        counter.dec();
      }
    }
  }
}
