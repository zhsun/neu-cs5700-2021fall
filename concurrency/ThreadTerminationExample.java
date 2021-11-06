package concurrency;

public class ThreadTerminationExample {
  public static void main(String[] args) {
    Thread blockingThread = new Thread(new BlockingTask());
    blockingThread.start();
    blockingThread.interrupt();

    Thread longComputeThread = new Thread(new LongComputeTask());
    longComputeThread.start();
    longComputeThread.interrupt();

    Thread daemonThread = new Thread(new LongComputeTask());
    daemonThread.setDaemon(true);
    daemonThread.start();

    System.out.println("Exiting main user thread.");
  }

  private static class BlockingTask implements Runnable {
    @Override
    public void run() {
      try {
        // Thread.sleep method can throw InterruptedException.
        Thread.sleep(50000);  // 50 seconds.
      } catch (InterruptedException e) {
        System.out.println("Exiting blocking thread");
      }
    }
  }

  private static class LongComputeTask implements Runnable {
    @Override
    public void run() {
      while (true) {
        // Very loooooong computation.
        // Need to check if there is interrupt signal.
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("Exiting long compute thread");
          break;
        }
      }
    }
  }
}
