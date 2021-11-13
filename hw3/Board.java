package snake.app;

import static snake.app.Config.BUF_SIZE;
import static snake.app.Config.BOARD_SIZE;
import static snake.app.Config.UNIT_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel {
  private static final Image DOT = new ImageIcon("hw3/images/dot.png").getImage();
  private static final Image APPLE = new ImageIcon("hw3/images/apple.png").getImage();
  private static final Image HEAD = new ImageIcon("hw3/images/head.png").getImage();

  private GameUpdateData gameData;

  public Board() {
    int size = UNIT_SIZE * BOARD_SIZE;
    setPreferredSize(new Dimension(size, size));
    setBorder(BorderFactory.createLineBorder(Color.BLUE));
    setBackground(Color.BLACK);
    gameData = GameUpdateData.getDefaultInstance();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (gameData.equals(GameUpdateData.getDefaultInstance())) {
      return;
    }
    renderApple(g);
    renderSnake(g);
    if (gameData.getGameOver()) {
      renderGameOver(g);
    }
  }

  public void updateGameData(GameUpdateData data) {
    gameData = data;
  }

  private void renderApple(Graphics g) {
    render(g, APPLE, gameData.getApple());
  }

  private void renderSnake(Graphics g) {
    gameData.getSnakeList()
        .forEach(p -> render(g, DOT, p));
  }

  private void renderGameOver(Graphics g) {
    g.setColor(Color.RED);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
    g.drawString("Game Over", 50, 50);
  }

  private void render(Graphics g, Image image, Coordinate p) {
    g.drawImage(image, p.getCol() * UNIT_SIZE, p.getRow() * UNIT_SIZE, this);
  }
}
