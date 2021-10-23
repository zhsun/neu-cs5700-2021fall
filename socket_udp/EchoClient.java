package echo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.Scanner;

public class EchoClient {
  private static final int SERVER_PORT = 8080;
  private static final int BUF_SIZE = 1024;

  public static void main(String... args) {
    System.out.println("UDP echo client");
    try (Scanner scanner = new Scanner(System.in);
         DatagramSocket clientSocket = new DatagramSocket()) {
      InetAddress addr = InetAddress.getByName("localhost");
      while (true) {
        System.out.print("Enter message: ");
        String msg = scanner.nextLine();
        if (msg.equals("quit")) {
          break;
        }
        DatagramPacket pkt = new DatagramPacket(msg.getBytes(), msg.length(), addr, SERVER_PORT);
        clientSocket.send(pkt);
        // Recv echo msg.
        byte[] buf = new byte[BUF_SIZE];
        DatagramPacket echoPkt = new DatagramPacket(buf, BUF_SIZE);
        clientSocket.receive(echoPkt);
        String echoMsg = new String(echoPkt.getData());
        System.out.println("Recv from server: " + echoMsg);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
