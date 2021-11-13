package hw;

import java.io.IOException;

// TODO.
public class StopAndWait extends TransportLayer {
  public StopAndWait(NetworkLayer networkLayer) {
    super(networkLayer);
  }

  @Override
  public void send(byte[] data) throws IOException {
  }

  @Override
  public byte[] recv() throws IOException {
    return null;
  }
}
