package hw;

import static java.util.stream.Collectors.toList;
import static hw.Calculator.eval;

import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class RequestHandler implements Runnable {
  private Socket clientSocket;

  public RequestHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    String client = String.format("[%s:%d]", clientSocket.getInetAddress(), clientSocket.getPort());
    System.out.println(String.format("Handle request from client %s", client));
    try {
      RequestReader reader = new RequestReader(clientSocket.getInputStream());
      List<String> expressions = reader.readExpressions();
      System.out.println("Total expressions: " + expressions.size());
      List<String> answers = expressions.stream()
          .map(e -> eval(e))
          .collect(toList());
      ResponseWriter writer = new ResponseWriter(clientSocket.getOutputStream());
      writer.writeResults(answers);
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
