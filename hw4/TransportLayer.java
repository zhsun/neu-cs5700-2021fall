package hw;

import java.io.Closeable;
import java.io.IOException;

public abstract class TransportLayer implements Closeable {
  protected NetworkLayer networkLayer;

  public TransportLayer(NetworkLayer networkLayer) {
    this.networkLayer = networkLayer;
  }

  public abstract void send(byte[] data) throws IOException;
  public abstract byte[] recv() throws IOException;

  @Override
  public void close() throws IOException {
    networkLayer.close();
  }
}
