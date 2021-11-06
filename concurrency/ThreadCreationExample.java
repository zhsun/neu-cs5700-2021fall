package concurrency;

public class ThreadCreationExample {
  public static void main(String[] args) {
    Thread thread = new Thread(() -> {
        System.out.println("Hello World in thread: " + Thread.currentThread());
        throw new RuntimeException("Internal error");
    });
    // Set thread name (optional).
    thread.setName("WorkerThread");
    // Set thread priority: NORM_PRIORITY, MAX_PRIORITY, MIN_PRIORITY.
    thread.setPriority(Thread.MAX_PRIORITY);
    // Set handler to deal with uncaught exceptions.
    thread.setUncaughtExceptionHandler((t, e) -> {
        System.out.println("Handling uncaught exception: " + t + " :: " + e);
      });
    thread.start();
    System.out.println("Thread: " + Thread.currentThread());
  }
}
