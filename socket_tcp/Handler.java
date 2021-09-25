package echo;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class Handler implements Runnable {
  private Socket clientSocket;

  public Handler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    String client = String.format("[%s:%d]", clientSocket.getInetAddress(), clientSocket.getPort());
    System.out.println(String.format("Handle client %s", client));
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(String.format("Recv [%s]: %s", client, line));
        writer.println(line);
      }
      System.out.println(String.format("Bye bye %s", client));
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

