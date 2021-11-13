package hw;

import static hw.Config.TIMEOUT_MSEC;
import static hw.Util.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

// DemoTransportLayer is an example of transport layer that deals with
// data loss but not bit error. The goal is the show synchronizations
// needed to complete SW and GBN.
public class DemoTransportLayer extends TransportLayer {
  private Semaphore sem;
  private ScheduledFuture<?> timer;
  private ScheduledExecutorService scheduler;

  public DemoTransportLayer(NetworkLayer networkLayer) {
    super(networkLayer);
    sem = new Semaphore(1);  // Guard to send 1 pkt at a time.
    scheduler = Executors.newScheduledThreadPool(1);
  }

  @Override
  public void send(byte[] data) throws IOException {
    try {
      sem.acquire();
      networkLayer.send(data);
      timer = scheduler.scheduleAtFixedRate(new RetransmissionTask(data),
                                            TIMEOUT_MSEC,
                                            TIMEOUT_MSEC,
                                            TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Start thread to read ACK.
    new Thread(() -> {
        try {
          byte[] ack = networkLayer.recv();
          timer.cancel(true);
          sem.release();
        } catch (Exception e) {
          e.printStackTrace();
        }
    }).start();
  }

  @Override
  public byte[] recv() throws IOException {
    byte[] data = networkLayer.recv();
    networkLayer.send(new byte[1]);  // 1 byte as ACK.
    return data;
  }

  @Override
  public void close() throws IOException {
    try {
      sem.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    super.close();
  }

  private class RetransmissionTask implements Runnable {
    private byte[] data;

    public RetransmissionTask(byte[] data) {
      this.data = data;
    }

    @Override
    public void run() {
      try {
        networkLayer.send(data);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
