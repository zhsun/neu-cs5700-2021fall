package snake.app;

import static snake.app.Config.BOARD_SIZE;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class GameState {
  private Position apple;
  private LinkedList<Position> snake;
  private Direction dir;
  private boolean gameOver;

  GameState() {
    initSnake();
    generateApple();
    dir = Direction.UP;
    gameOver = false;
  }

  public Position getApplePosition() {
    return apple;
  }

  public Stream<Position> getSnakePosition() {
    return snake.stream();
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public void updateDirection(Direction newDir) {
    Position next = Position.copy(snake.getFirst()).move(newDir);
    if (next.equals(snake.get(1))) {
      return;
    }
    dir = newDir;
  }

  public void moveSnake() {
    if (gameOver) {
      return;
    }
    Position nextHead = Position.copy(snake.getFirst()).move(dir);
    if (isInSnakeButNotHeadOrTail(nextHead)) {
      gameOver = true;
      return;
    }
    snake.addFirst(nextHead);
    if (nextHead.equals(apple)) {
      generateApple();
      return;
    }
    snake.removeLast();
  }

  private void initSnake() {
    Position head = Position.random(BOARD_SIZE);
    snake = Stream.of(head,
                      Position.copy(head).move(Direction.DOWN),
                      Position.copy(head).move(Direction.DOWN).move(Direction.DOWN))
        .collect(Collectors.toCollection(LinkedList::new));
  }

  private void generateApple() {
    do {
      apple = Position.random(BOARD_SIZE);
    } while (isInSnake(apple));
  }

  private boolean isInSnake(Position o) {
    return snake.stream().anyMatch(p -> p.equals(o));
  }

  private boolean isInSnakeButNotHeadOrTail(Position o) {
    for (Position p : snake) {
      if (p.equals(snake.getFirst()) || p.equals(snake.getLast())) {
        continue;
      }
      if (p.equals(o)) {
        return true;
      }
    }
    return false;
  }
}
