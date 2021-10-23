package snake.app;

import java.util.concurrent.ThreadLocalRandom;


class Position {
  private int row;
  private int col;
  private int size;

  private Position(int row, int col, int size) {
    this.row = row;
    this.col = col;
    this.size = size;
  }

  public static Position random(int size) {
    return new Position(ThreadLocalRandom.current().nextInt(size),
                        ThreadLocalRandom.current().nextInt(size),
                        size);
  }

  public static Position copy(Position o) {
    return new Position(o.getRow(), o.getCol(), o.getSize());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }
    Position op = (Position) o;
    return op.getRow() == row && op.getCol() == col && op.getSize() == size;
  }

  @Override
  public int hashCode() {
    return (row * 31 + col) * 31 + size;
  }

  @Override
  public String toString() {
    return String.format("([%d,%d],%d)", row, col, size);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getSize() {
    return size;
  }

  public Position move(Direction d) {
    switch (d) {
      case UP:
        row = (size + row - 1) % size;
        break;
      case RIGHT:
        col = (col + 1) % size;
        break;
      case DOWN:
        row = (row + 1) % size;
        break;
      case LEFT:
        col = (size + col - 1) % size;
        break;
    }
    return this;
  }
}
