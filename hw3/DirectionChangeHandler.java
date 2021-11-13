package snake.app;

import static snake.app.Config.CLIENT_PORT;
import static snake.app.Config.SERVER_PORT;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


class DirectionChangeHandler extends KeyAdapter {
  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        sendDirectionUpdate(Direction.LEFT);
        break;
      case KeyEvent.VK_RIGHT:
        sendDirectionUpdate(Direction.RIGHT);
        break;
      case KeyEvent.VK_UP:
        sendDirectionUpdate(Direction.UP);
        break;
      case KeyEvent.VK_DOWN:
        sendDirectionUpdate(Direction.DOWN);
        break;
    }
  }

  private void sendDirectionUpdate(Direction d) {
    ControlData update = ControlData.newBuilder()
        .setType(ControlData.Type.UPDATE_DIRECTION)
        .setPort(CLIENT_PORT)
        .setDirection(d)
        .build();
    NetUtil.sendMessage(update.toByteArray(), SERVER_PORT);
  }
}
