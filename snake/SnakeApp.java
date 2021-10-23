package snake.app;

import static snake.app.Config.GAME_SPEED_MS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JFrame;


class SnakeApp extends JFrame {
  private final ScheduledExecutorService scheduler;
  private final Board board;

  SnakeApp() {
    setTitle("Snake Game");
    board = new Board();
    add(board);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Size the window so that all its contents are at or above their
    // preferred sizes.
    pack();
    addKeyListener(new DirectionChangeHandler());

    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(this::runOnce, /* initialDelay */ 0, GAME_SPEED_MS, MILLISECONDS);
  }

  public static void main(String... args) {
    SnakeApp app = new SnakeApp();
    app.setVisible(true);
  }

  private void runOnce() {
    GameState.get().moveSnake();
    board.repaint();
  }
}
