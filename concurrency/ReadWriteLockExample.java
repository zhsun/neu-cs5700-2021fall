package concurrency;

import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ReadWriteLockExample {
  public static final int HIGHEST_PRICE = 1000;

  public static void main(String[] args) throws InterruptedException {
    InventoryDatabase db = new InventoryDatabase();
    Random rand = new Random();
    for (int i = 0; i < 100000; i++) {
      db.addItem(rand.nextInt(HIGHEST_PRICE));
    }

    Thread writer = new Thread(() -> {
        while (true) {
          db.addItem(rand.nextInt(HIGHEST_PRICE));
          db.removeItem(rand.nextInt(HIGHEST_PRICE));
          try {
            Thread.sleep(10);  // 10ms.
          } catch (InterruptedException e) {
          }
        }
    });
    writer.setDaemon(true);
    writer.start();

    int numberOfReaders = 5;
    for (int i = 0; i < numberOfReaders; i++) {
      Thread reader = new Thread(() -> {
          while (true) {
            int high = rand.nextInt(HIGHEST_PRICE);
            int low = high == 0 ? 0 : rand.nextInt(high);
            db.getNumberOfItemsInPriceRange(low, high);
          }
      });
      reader.setDaemon(true);
      reader.start();
    }

    Thread.sleep(10000);
    System.out.println("After 10s, the number of read operation is " + db.getNumberOfRead());
  }

  public static class InventoryDatabase {
    private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
    private int numberOfRead = 0;
    // If using ReentrantLock, performance will be worse.
    // private ReentrantLock lock = new ReentrantLock();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public int getNumberOfItemsInPriceRange(int low, int high) {
      readLock.lock();
      try {
        ++numberOfRead;
        Integer fromKey = priceToCountMap.ceilingKey(low);
        Integer toKey = priceToCountMap.floorKey(high);
        if (fromKey == null || toKey == null) return 0;
        int total = priceToCountMap.subMap(fromKey, true, toKey, true)
            .values().stream().collect(Collectors.summingInt(x -> x));
        return total;
      } finally {
        readLock.unlock();
      }
    }

    public void addItem(int price) {
      writeLock.lock();
      try {
        Integer count = priceToCountMap.get(price);
        if (count == null) {
          priceToCountMap.put(price, 1);
        } else {
          priceToCountMap.put(price, count + 1);
        }
      } finally {
        writeLock.unlock();
      }
    }

    public void removeItem(int price) {
      writeLock.lock();
      try {
        Integer count = priceToCountMap.get(price);
        if (count == null || count == 1) {
          priceToCountMap.remove(price);
        } else {
          priceToCountMap.put(price, count - 1);
        }
      } finally {
        writeLock.unlock();
      }
    }

    public int getNumberOfRead() {
      return numberOfRead;
    }
  }
}
