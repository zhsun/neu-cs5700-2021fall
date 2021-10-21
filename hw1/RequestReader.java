package hw;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestReader {
  private static final int BUFSIZE = 16;

  private InputStream in;
  private byte[] buf;

  public RequestReader(InputStream in) {
    this.in = in;
    this.buf = new byte[BUFSIZE];
  }

  public List<String> readExpressions() throws IOException {
    List<String> expressions = new ArrayList<>();
    int numExpressions = readNumber();
    for (int i = 0; i < numExpressions; ++i) {
      int len = readNumber();
      String expression = readExpression(len);
      expressions.add(expression);
    }
    return expressions;
  }

  private int readNumber() throws IOException {
    readNBytes(2);
    int n = 0;
    n |= (buf[0] & 0xFF) << 8;
    n |= (buf[1] & 0xFF);
    return n;
  }

  private String readExpression(int len) throws IOException {
    StringBuilder sb = new StringBuilder();
    while (len > 0) {
      int targetLen = Math.min(len, BUFSIZE);
      readNBytes(targetLen);
      sb.append(new String(buf, 0, targetLen));
      len -= targetLen;
    }
    return sb.toString();
  }

  private void readNBytes(int n) throws IOException {
    int offset = 0;
    while (n > 0) {
      int len = in.read(buf, offset, n);
      offset += len;
      n -= len;
    }
  }
}
