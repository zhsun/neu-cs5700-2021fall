package echo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;

public class EchoServer {
  private static final int PORT = 8080;
  private static final int BUF_SIZE = 1024;

  public static void main(String... args) {
    try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
      System.out.println("UDP echo server");
      while (true) {
        byte[] buf = new byte[BUF_SIZE];
        DatagramPacket pkt = new DatagramPacket(buf, BUF_SIZE);
        serverSocket.receive(pkt);
        InetAddress addr = pkt.getAddress();
        int port = pkt.getPort();
        String msg = new String(pkt.getData());
        System.out.println(String.format("[%s:%d]: %s", addr, port, msg));
        // Echo back the same message.
        DatagramPacket echoPkt = new DatagramPacket(msg.getBytes(), msg.length(), addr, port);
        serverSocket.send(echoPkt);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
