package echo;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EchoServer {
  public static final int SERVER_PORT = 8080;
  
  public static void main(String... args) throws IOException {
    System.out.println("Echo server");
    ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
    try {
      System.out.println("Start to accept incoming connections");
      while (true) {
        Socket clientSocket = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println("Recv: " + line);
          writer.println(line);
        }
        clientSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      serverSocket.close();
    }
  }
}
