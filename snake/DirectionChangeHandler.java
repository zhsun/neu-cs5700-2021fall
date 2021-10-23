package snake.app;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


class DirectionChangeHandler extends KeyAdapter {
  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        GameState.get().updateDirection(Direction.LEFT);
        break;
      case KeyEvent.VK_RIGHT:
        GameState.get().updateDirection(Direction.RIGHT);
        break;
      case KeyEvent.VK_UP:
        GameState.get().updateDirection(Direction.UP);
        break;
      case KeyEvent.VK_DOWN:
        GameState.get().updateDirection(Direction.DOWN);
        break;
    }
  }
}
