package hw;

public class TransportLayerFactory {
  public static TransportLayer create(String name, int localPort, int remotePort) throws Exception {
    if (name.equals("dummy")) {
      return new DummyTransportLayer(new NetworkLayer(localPort, remotePort));
    }
    if (name.equals("demo")) {
      return new DemoTransportLayer(new NetworkLayer(localPort, remotePort));
    }
    if (name.equals("sw")) {
      return new StopAndWait(new NetworkLayer(localPort, remotePort));
    }
    if (name.equals("gbn")) {
      return new GoBackN(new NetworkLayer(localPort, remotePort));
    }
    throw new Exception(name + "is not supported");
  }
}
