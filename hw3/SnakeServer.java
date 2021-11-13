package snake.app;

import static snake.app.Config.GAME_SPEED_MS;
import static snake.app.Config.GAME_UPDATE_MS;
import static snake.app.Config.SERVER_PORT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


class SnakeServer {
  private final Map<Integer, GameState> games;
  private final ScheduledExecutorService scheduler;

  SnakeServer() {
    games = new ConcurrentHashMap<>();
    scheduler = Executors.newScheduledThreadPool(5);
    // Thread to update games.
    scheduler.scheduleAtFixedRate(() -> {
        games.values().forEach(GameState::moveSnake);
      },
      /* initialDelay */ 0,
      GAME_SPEED_MS,
      MILLISECONDS);
    // Thread to send game updates.
    scheduler.scheduleAtFixedRate(() -> {
        games.forEach(this::sendGameUpdate);
      },
      /* initialDelay */ 0,
      GAME_UPDATE_MS,
      MILLISECONDS);
    // Thread to show debug info.
    scheduler.scheduleAtFixedRate(() -> {
        System.out.println(String.format("There are %d active games", games.size()));
      },
      /* initialDelay */ 0,
      5,
      SECONDS);
    // Thread handles incoming control messages.
    NetUtil.startMessageHandler(this::handleMessage, SERVER_PORT);
    System.out.println("SnakeServer start.");
  }

  private void sendGameUpdate(int port, GameState game) {
    GameUpdateData data = GameUpdateData.newBuilder()
        .setGameOver(game.isGameOver())
        .setApple(Coordinate.newBuilder()
                  .setRow(game.getApplePosition().getRow())
                  .setCol(game.getApplePosition().getCol())
                  .build())
        .addAllSnake(game.getSnakePosition()
                     .map(p -> Coordinate.newBuilder().setRow(p.getRow()).setCol(p.getCol()).build())
                     .collect(Collectors.toList()))
        .build();
    NetUtil.sendMessage(data.toByteArray(), port);
    if (data.getGameOver()) {
      // Remove game once it is completed.
      System.out.println("Remove game with port " + port);
      games.remove(port);
    }
  }

  private void handleMessage(byte[] d) {
    try {
      ControlData data = ControlData.parseFrom(d);
      switch (data.getTypeValue()) {
        case ControlData.Type.CREATE_GAME_VALUE:
          System.out.println(String.format("Create game [%d]", data.getPort()));
          games.put(data.getPort(), new GameState());
          break;
        case ControlData.Type.UPDATE_DIRECTION_VALUE:
          if (games.containsKey(data.getPort())) {
            System.out.println(String.format("Direction update [%d:%s]", data.getPort(), data.getDirection()));
            games.get(data.getPort()).updateDirection(data.getDirection());
          }
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String... args) {
    new SnakeServer();
  }
}
