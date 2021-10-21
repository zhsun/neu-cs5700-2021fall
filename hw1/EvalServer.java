package hw;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

public class EvalServer {
  private static final int PORT = 8080;
  
  public static void main(String... args) throws Exception {
    System.out.println("Starting EvalSever");
    ServerSocket serverSocket = new ServerSocket(PORT);
    try {
      System.out.println("Listen incoming connections on port " + PORT);
      while (true) {
        Socket clientSocket = serverSocket.accept();
        new Thread(new RequestHandler(clientSocket)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      serverSocket.close();
    }
  }
}
