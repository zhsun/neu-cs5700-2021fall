package snake.app;

import static snake.app.Config.CLIENT_PORT;
import static snake.app.Config.SERVER_PORT;
import static snake.app.Config.GAME_SPEED_MS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JFrame;

class SnakeClient extends JFrame {
  private final ScheduledExecutorService scheduler;
  private final Board board;

  SnakeClient() {
    setTitle("Snake Game");
    board = new Board();
    add(board);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Size the window so that all its contents are at or above their
    // preferred sizes.
    pack();
    addKeyListener(new DirectionChangeHandler());
    // Thread to render game board.
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(() -> {
        board.repaint();
      },
      /* initialDelay */ 0,
      GAME_SPEED_MS,
      MILLISECONDS);
    // Thread to get game state updates.
    NetUtil.startMessageHandler(this::handleMessage, CLIENT_PORT);
    startGame();
  }

  private void startGame() {
    ControlData data = ControlData.newBuilder()
        .setType(ControlData.Type.CREATE_GAME)
        .setPort(CLIENT_PORT)
        .build();
    NetUtil.sendMessage(data.toByteArray(), SERVER_PORT);
  }

  private void handleMessage(byte[] d) {
    try {
      GameUpdateData data = GameUpdateData.parseFrom(d);
      board.updateGameData(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String... args) {
    SnakeClient client = new SnakeClient();
    client.setVisible(true);
  }
}
