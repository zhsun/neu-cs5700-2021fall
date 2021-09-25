package echo;

import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EchoClient {
  public static final int SERVER_PORT = 8080;
  
  public static void main(String... args) {
    System.out.println("Echo client");
    try {
      InetAddress local = InetAddress.getLocalHost();  // Can use InetAddress.getByName().
      System.out.println("LocalHost: " + local);
      Socket clientSocket = new Socket(local, SERVER_PORT);
      PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      System.out.println("Connected to server " + clientSocket.getInetAddress()
                         + " port " + clientSocket.getPort());
      Scanner scanner = new Scanner(System.in);
      while (true) {
        System.out.print("Enter text: ");
        String line = scanner.nextLine();
        if ("quit".equalsIgnoreCase(line)) {
          break;
        }
        writer.println(line);
        String response = reader.readLine();
        System.out.println("Server response: " + response);
      }
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
