package snake.app;

import java.util.concurrent.ThreadLocalRandom;


enum Direction {
  UP,
  RIGHT,
  DOWN,
  LEFT;

  public static Direction random() {
    return Direction.values()[ThreadLocalRandom.current().nextInt(4)];
  }
}
