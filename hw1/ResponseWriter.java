package hw;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ResponseWriter {
  private static final int MAX_WRITE_SIZE = 16;

  private OutputStream out;
  private byte[] buf;

  public ResponseWriter(OutputStream out) {
    this.out = out;
    this.buf = new byte[2];
  }

  public void writeResults(final List<String> answers) throws IOException {
    writeNumber(answers.size());
    for (String answer : answers) {
      writeNumber(answer.length());
      writeResult(answer);
    }
  }

  private void writeNumber(int n) throws IOException {
    buf[0] = (byte) ((n >> 8) & 0xFF);
    buf[1] = (byte) (n & 0xFF);
    out.write(buf, 0, 2);
  }

  private void writeResult(String answer) throws IOException {
    byte[] b = answer.getBytes();
    int offset = 0;
    while (offset < b.length) {
      int len = Math.min(b.length - offset, MAX_WRITE_SIZE);
      out.write(b, offset, len);
      offset += len;
    }
  }
}
